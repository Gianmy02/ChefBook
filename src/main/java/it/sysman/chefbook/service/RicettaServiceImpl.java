package it.sysman.chefbook.service;

import it.sysman.chefbook.dto.RicettaDto;
import it.sysman.chefbook.dto.TransferRequestDto;
import it.sysman.chefbook.entity.Autore;
import it.sysman.chefbook.entity.Ricetta;
import it.sysman.chefbook.entity.TransferRequest;
import it.sysman.chefbook.exception.AutoreNotFoundException;
import it.sysman.chefbook.exception.ForbiddenActionException;
import it.sysman.chefbook.exception.RicettaNotFoundException;
import it.sysman.chefbook.repository.AutoreRepository;
import it.sysman.chefbook.repository.RicettaRepository;
import it.sysman.chefbook.repository.TransferRequestRepository;
import it.sysman.chefbook.utils.RicettaMapper;
import it.sysman.chefbook.utils.TransferRequestStatusEnum;
import it.sysman.chefbook.utils.TransferTokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.List;

@Service
public class RicettaServiceImpl implements RicettaService{

    @Autowired
    private RicettaRepository ricettaRepository;

    @Autowired
    private TransferRequestRepository transferRequestRepository;

    @Autowired
    private RicettaMapper ricettaMapper;

    @Autowired
    private AutoreRepository autoreRepository;

    public void addRicetta(RicettaDto dto){
        Ricetta r = ricettaMapper.ricettaDtoToRicetta(dto);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Autore autore = autoreRepository.findByEmail(email);
        if(autore == null)
            throw new AutoreNotFoundException("Autore: "+email);
        r.setAutore(autore);
        ricettaRepository.save(r);
    }


    public boolean removeRicetta(int id){
        return invokePostControl("deleteById",id, null);
    }


    public boolean editRicetta(RicettaDto dto){
        Ricetta r = ricettaMapper.ricettaDtoToRicetta(dto);
        return invokePostControl("save", r.getId(), r);
    }


    public RicettaDto getRicettaByName(String nome){
        Ricetta r = ricettaRepository.findByNome(nome);
        if(r == null)
            throw new RicettaNotFoundException("Ricetta not found");
        return ricettaMapper.ricettaToRicettaDto(r);
    }


    public List<RicettaDto> getAllRicette(){
        return ricettaMapper.ricetteToRicetteDto(ricettaRepository.findAll());
    }

    @Override
    public void transferRicetta(TransferRequestDto dto) {
        Ricetta ricetta = ricettaRepository.findById(dto.getIdRicetta()).get();
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(autoreRepository.findByEmail(dto.getEmailDestinatario())==null)
            throw new AutoreNotFoundException("Autore: "+dto.getEmailDestinatario());
        if(!email.equals(ricetta.getAutore().getEmail()))
            throw new ForbiddenActionException("Forbidden Action for: "+email);
        TransferRequest transferRequest = TransferRequest
                .builder()
                .token(TransferTokenGenerator.generate())
                .id(0)
                .mittente(email)
                .destinatario(dto.getEmailDestinatario())
                .ricetta(ricetta)
                .status(TransferRequestStatusEnum.ACTIVE.getValue())
                .build();
        transferRequestRepository.save(transferRequest);
    }

    @Override
    public void acceptRicetta(String token) {
        TransferRequest req = transferRequestRepository.findByToken(token);
        if(!req.getStatus().equals(TransferRequestStatusEnum.ACTIVE.getValue()))
            throw new ForbiddenActionException("Forbidden Action for: "+token);
        Ricetta ricetta = req.getRicetta();
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!email.equals(req.getDestinatario()))
            throw new ForbiddenActionException("Forbidden Action for: "+email);
        ricetta.setAutore(autoreRepository.findByEmail(email));
        ricettaRepository.save(ricetta);
        req.setStatus(TransferRequestStatusEnum.USED.getValue());
        transferRequestRepository.save(req);
    }

    @Override
    public void declineRicetta(String token) {
        TransferRequest req = transferRequestRepository.findByToken(token);
        if(!req.getStatus().equals(TransferRequestStatusEnum.ACTIVE.getValue()))
            throw new ForbiddenActionException("Forbidden Action for: "+token);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!email.equals(req.getDestinatario()))
            throw new ForbiddenActionException("Forbidden Action for: "+email);
        req.setStatus(TransferRequestStatusEnum.DECLINED.getValue());
        transferRequestRepository.save(req);
    }

    @Override
    public void revokeTransferRicetta(String token) {
        TransferRequest req = transferRequestRepository.findByToken(token);
        if(!req.getStatus().equals(TransferRequestStatusEnum.ACTIVE.getValue()))
            throw new ForbiddenActionException("Forbidden Action");
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!email.equals(req.getMittente()))
            throw new ForbiddenActionException("Forbidden Action for: "+email);
        req.setStatus(TransferRequestStatusEnum.REVOKED.getValue());
        transferRequestRepository.save(req);
    }

    //programmazione riflessiva
    private boolean invokePostControl(String method, int id, Ricetta ricetta) {
        if (!ricettaRepository.existsById(id))
            return false;
        else {
            if (method.contains("delete")) {
                try {
                    Method[] methods = this.ricettaRepository.getClass()
                            .getDeclaredMethods();

                    for (Method m : methods) {
                        if (m.getName().contains(method)) {
                            m.invoke(ricettaRepository, id);
                        }
                    }
                    return true;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else if (method.contains("save")) {
                try {
                    Method[] methods = this.ricettaRepository.getClass()
                            .getDeclaredMethods();

                    for (Method m : methods) {
                        if (m.getName().contains(method)) {
                            m.invoke(ricettaRepository, ricetta);  //fiduciosi che sia la prima funzione (save) che si trova
                            break;
                        }
                    }
                    return true;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            return false;
        }
    }
}
