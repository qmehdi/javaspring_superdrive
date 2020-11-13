package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CredentialMapper {

    /**
     * Returns a single credential row
     * @param credentialid
     * @param userid
     * @return
     */
    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialid} and userid = #{userid}")
    Credential getCredential(Integer credentialid, Integer userid);

    /**
     * Returns all credentials for a given userid
     * @param userid
     * @return
     */
    @Select("SELECT * FROM CREDENTIALS where userid = #{userid}")
    List<Credential> getAllCredentials(Integer userid);

    /**
     * Delete a given credential
     * @param credentialid
     * @return
     */
    @Select("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    Credential deleteCredential(Integer credentialid);

    /**
     * Insert a credential
     * @param credential
     * @return
     */
    @Insert("INSERT INTO CREDENTIALS (credentialId, url, username, key, password, userid) VALUES (#{credentialId}, #{url}, #{username}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insertCredential(Credential credential);

    /**
     * Get all credentials in the universe
     * @return
     */
    @Select("SELECT * FROM CREDENTIALS")
    List<Credential> getCredentialUniverse();


}
