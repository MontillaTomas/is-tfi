const API_URL = "http://localhost:8080/api/v1/pacientes/";
import Cookie from 'js-cookie'

const getPaciente = async (dni) => {

    const token = Cookie.get("tokenAcceso");
    const response = await fetch(`${API_URL}${dni}`, {
        method: "GET",
        headers: {
            Authorization: `Bearer ${token}`,
        },
    });
    const data = await response.json();
    if (!response.ok) {

        throw new Error(`${data.message}`);
    }
    return data
}

export const pacienteService = {
    getPaciente,
}