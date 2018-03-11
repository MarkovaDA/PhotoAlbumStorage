package darya.markova.photostorage.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import darya.markova.photostorage.security.util.exception.ForbiddenExceptionResponseInfo;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationTokenFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private UserDetailsService userDetailsService;
    private JwtTokenUtil jwtTokenUtil;
    private static final String TOKEN_HEADER = "token";

    public JwtAuthorizationTokenFilter(UserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        logger.debug("processing authentication for '{}'", request.getRequestURL());

        final String requestHeader = request.getHeader(this.TOKEN_HEADER);

        String username = null;
        String authToken = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            authToken = requestHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(authToken);
            } catch (SignatureException e) {
                String errorMessage = "an error occured during getting username from token - token is invalid";
                logger.error(errorMessage, e);
                processForbiddenError(response, e, errorMessage);
            } catch (IllegalArgumentException e) {
                String errorMessage = "an error occured during getting username from token";
                logger.error(errorMessage, e);
                processForbiddenError(response, e, errorMessage);
            } catch (ExpiredJwtException e) {
                String warningMessage = "the token is expired and not valid anymore";
                logger.warn(warningMessage, e);
                processForbiddenError(response, e, warningMessage);
            }
        } else {
            String warnMessage = "couldn't find bearer string, will ignore the header";
            logger.warn(warnMessage);
            SecurityContextHolder.getContext().setAuthentication(null);
            username = null;
//            Exception ex = new Exception(String.format("Access denied to URL '%s'.", request.getRequestURL().toString()));
//            processForbiddenError(response, ex, warnMessage);
        }

        logger.debug("checking authentication for user '{}'", username);
        if (username != null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            try {
                jwtTokenUtil.validateToken(authToken, userDetails);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                logger.info("authorizated user '{}', setting security context", username);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception ex) {
                String errorMessage = String.format("Exception while trying to authenticate user '%s'", username);
                SecurityContextHolder.getContext().setAuthentication(null);
                processForbiddenError(response, ex, errorMessage);
            }
        }

        chain.doFilter(request, response);
    }

    private String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    private void processForbiddenError(HttpServletResponse response, Exception ex, String errorMessage)
            throws IOException {
        ForbiddenExceptionResponseInfo exceptionInfo = new ForbiddenExceptionResponseInfo(ex, errorMessage);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.getWriter().write(convertObjectToJson(exceptionInfo));
        response.getWriter().close();
    }
}
