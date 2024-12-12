Feature: Iniciar sesión en el sistema de la clínica
  Como médico
  Quiero iniciar sesión en el sistema de la clínica
  Para poder visualizar las historias clínicas de mis pacientes y crear nuevas evoluciones

  Background: Existe un médico registrado en el sistema
    Given existe un medico en el sistema con el usuario "email@email.com" con la contasenia "12345"

  Scenario:
    When el usuario intenta ingresar al sistema con el email "email@email.com" y contrasenia "12345"
    Then el usuario obtiene acceso al sistema

  Scenario:
    When el usuario intenta ingresar al sistema con el email "email@email.com" y contrasenia "contraseñaIncorrecta"
    Then el médico no obtiene acceso al sistema y se indica que las credenciales son incorrectas

  Scenario:
    When el usuario intenta ingresar al sistema con el email "emailincorrecto@example.com" y contrasenia "12345"
    Then el médico no obtiene acceso al sistema y se indica que las credenciales son incorrectas