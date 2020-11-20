package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

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
     * @Param userid
     * @return
     */
    @Select("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid} AND userid = #{userid}")
    Credential deleteCredential(Integer credentialid, Integer userid);

    /**
     * Insert a credential
     * @param credential
     * @return
     */
    @Insert("INSERT INTO CREDENTIALS (credentialId, url, username, key, password, userid) VALUES (#{credentialId}, #{url}, #{username}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insertCredential(Credential credential);

    /**
     * Update a credential that already exists in the db
     * @param credential
     * @return
     */
    @Update("UPDATE CREDENTIALS SET url=#{url}, username=#{username}, key=#{key}, password=#{password} WHERE credentialId=#{credentialId} AND userid = #{userid}")
    int updateCredential(Credential credential);
}
