package com.citizen.server.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.citizen.server.mgr.VUserProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    // TODO we have to take this from property
    private static final String JWT_SECRET_KEY = "strongpassword";
    private static final String JWT_ISSUER = "asm";
    private static final String JWT_CLAIMS_USER_NAME = "USERNAME";
    private static final String JWT_CLAIMS_USER_ROLES = "ROLES";

    private static Algorithm algorithm = Algorithm.HMAC384(JWT_SECRET_KEY);

    public static String generateToken(VUserProfile vUserProfile) {
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        // Add one year to current date
        // TODO we have too take this from property
        calendar.add(Calendar.YEAR, 1);
        Date expireDate = calendar.getTime();

        String token = JWT.create().withIssuer(JWT_ISSUER).withClaim(JWT_CLAIMS_USER_NAME, vUserProfile.getUsername())
                .withClaim(JWT_CLAIMS_USER_ROLES, vUserProfile.getRoles()).withExpiresAt(expireDate).sign(algorithm);
        logger.info("Generated token=" + token);
        return token;
    }

    public static DecodedJWT validateToken(String token) {
        DecodedJWT decodedJWT;
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    // specify any specific claim validations
                    .withIssuer(JWT_ISSUER)
                    // reusable verifier instance
                    .build();

            decodedJWT = verifier.verify(token);
            logger.info("Got decodedJWT" + decodedJWT);

            return decodedJWT;
        } catch (JWTVerificationException exception) {
            // Invalid signature/claims
            logger.error("Got exception for validateToken token=" + token, exception);
            return null;
        }
    }

    /***
     * @param decodedJWT
     * @return
     */
    public static String getUsername(DecodedJWT decodedJWT) {
        Map<String, Claim> claims = decodedJWT.getClaims();
        Claim userNameClaim = claims.get(JWT_CLAIMS_USER_NAME);
        String userName = userNameClaim.asString();
        return userName;
    }

    public static void main(String[] args) {
        VUserProfile vUserProfile = new VUserProfile();
        vUserProfile.setUsername("admin");
        vUserProfile.setRoles(Arrays.asList(new String[]{"GOVERNOR"}));
        String token = generateToken(vUserProfile);
        DecodedJWT decodedJWT = validateToken(token);
        System.out.println(decodedJWT);
    }

}

