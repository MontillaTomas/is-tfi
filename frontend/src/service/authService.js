import Cookie from 'js-cookie'
const API_BASE_URL = "http://localhost:8080";

const login  = async(user)=>{
    
    const {email,contrasena} = user;
    const response = await fetch(`${API_BASE_URL}/api/v1/autenticacion/login`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({ email, contrasena }),
    });
    
    if (!response.ok) throw new Error('Usuario o contraseña incorrectas'); 

    const userData = await response.json();

    Cookie.set('tokenAcceso', userData.tokenAcceso, { expires: userData.expiraEn / (1000 * 60 * 60 * 24) });
    return userData;
}


export const authService = {login}