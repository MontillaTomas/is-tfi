import com.example.is_tfi.dominio.Medico;
import com.example.is_tfi.dominio.Usuario;
import com.example.is_tfi.dto.AutenticacionPeticionDTO;
import com.example.is_tfi.repositorio.impl.RepositorioMedicoImpl;
import com.example.is_tfi.repositorio.impl.RepositorioUsuarioImpl;
import com.example.is_tfi.servicio.AuthenticationService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public class InicioSesionStepdefs {
    private final RepositorioMedicoImpl repositorioMedico;
    private final RepositorioUsuarioImpl repositorioUsuario;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationService authenticationService;

    private Medico medicoAutenticado;
    private Exception excepcionCapturada;

    public InicioSesionStepdefs() {
        this.repositorioMedico = Mockito.mock(RepositorioMedicoImpl.class);
        this.repositorioUsuario = Mockito.mock(RepositorioUsuarioImpl.class);
        this.authenticationManager = Mockito.mock(AuthenticationManager.class);
        this.passwordEncoder = Mockito.mock(PasswordEncoder.class);

        this.authenticationService = new AuthenticationService(
                repositorioUsuario,
                authenticationManager,
                passwordEncoder
        );
    }

    @Given("existe un medico en el sistema con el usuario {string} con la contasenia {string}")
    public void existeUnMedicoEnElSistemaConElUsuarioConLaContasenia(String email, String contrasenia) {
        Usuario usuarioMock = new Usuario(email, contrasenia);

        Mockito.when(repositorioUsuario.buscarUsuarioPorEmail(email))
                .thenReturn(Optional.of(usuarioMock));

        Mockito.when(repositorioMedico.buscarMedicoPorEmailDeUsuario(email))
                .thenReturn(Optional.of(new Medico(
                        44747797L,
                        20447477972L,
                        "Gregory House",
                        null,
                        email,
                        "+123123123",
                        null,
                        123456,
                        "Clinico"
                )));
    }

    @When("el usuario intenta ingresar al sistema con el email {string} y contrasenia {string}")
    public void elUsuarioIntentaIngresarAlSistemaConElEmailYContraseña(String email, String contrasenia) {
        AutenticacionPeticionDTO dto = new AutenticacionPeticionDTO();
        dto.setEmail(email);
        dto.setContrasena(contrasenia);

        try {
            if ("contraseñaIncorrecta".equals(contrasenia) || "emailincorrecto@example.com".equals(email)) {
                Mockito.when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
                        .thenThrow(new BadCredentialsException("Credenciales inválidas"));
            } else {
                Authentication authentication = Mockito.mock(Authentication.class);
                Mockito.when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
                        .thenReturn(authentication);
            }

            medicoAutenticado = authenticationService.authenticate(dto);
            excepcionCapturada = null;
        } catch (BadCredentialsException e) {
            excepcionCapturada = e;
            medicoAutenticado = null;
        }
    }

    @Then("el usuario obtiene acceso al sistema")
    public void elUsuarioObtieneAccesoAlSistema() {
        Assertions.assertNull(excepcionCapturada, "No debería haber ocurrido ninguna excepción");
        Assertions.assertNotNull(medicoAutenticado, "El médico debería haber sido autenticado");
    }

    @Then("el médico no obtiene acceso al sistema y se indica que las credenciales son incorrectas")
    public void elMédicoNoObtieneAccesoAlSistemaYSeIndicaQueLasCredencialesSonIncorrectas() {
        Assertions.assertNull(medicoAutenticado, "El médico no debería haber sido autenticado");
        Assertions.assertNotNull(excepcionCapturada, "Debería haber ocurrido una excepción de credenciales");
        Assertions.assertTrue(excepcionCapturada instanceof BadCredentialsException,
                "La excepción debería ser de credenciales inválidas");
    }
}