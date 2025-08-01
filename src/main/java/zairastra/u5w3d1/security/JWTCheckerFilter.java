package zairastra.u5w3d1.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import zairastra.u5w3d1.entities.Employee;
import zairastra.u5w3d1.exceptions.UnauthorizedException;
import zairastra.u5w3d1.services.EmployeesService;
import zairastra.u5w3d1.tools.JWTTools;

import java.io.IOException;

@Component
public class JWTCheckerFilter extends OncePerRequestFilter {
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private EmployeesService employeesService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //accedo all'header, sezione authorization
        String authHeader = request.getHeader("Authorization");
        //verifico che il token ci sia e che sia scritto nella maniera corretta l'incipit per ricavarlo
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new UnauthorizedException("Insert a valid token");

        //ricavo il token
        String extractedToken = authHeader.replace("Bearer ", "");
        //lo verifico
        jwtTools.verifyToken(extractedToken);

        //AUTHORIZATION
        //trovo l'impiegato ricavando id da token
        String employeeId = jwtTools.extractId(extractedToken);
        Employee authorizedEmployee = this.employeesService.findEmployeeById(Long.parseLong(employeeId));

        //associo authorizedEmployee al SecurityContext
        Authentication authentication = new UsernamePasswordAuthenticationToken(authorizedEmployee, null, authorizedEmployee.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //filtro
        filterChain.doFilter(request, response);
    }

    @Override
    //escludo dal filtraggio tutti gli endpoint ch epassano per auth
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}
