package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {

    private final CredentialMapper credentialMapper;

    // Constructor
    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    // INSERT
    public int insertCredentialIntoDB(Credential credential) {
        return credentialMapper.insertCredential(credential);
    }

    // SELECT * WHERE
    public List<Credential> getAllCredentialsFromDB(Integer userid) {
        return credentialMapper.getAllCredentials(userid);
    }

    // DELETE
    public void deleteCredential(Integer credentialId, Integer userid) {
        credentialMapper.deleteCredential(credentialId, userid);
    }

    // UPDATE
    public int updateCredentialInDB(Credential updatedCredentialForm) {
        return credentialMapper.updateCredential(updatedCredentialForm);
    }
}
