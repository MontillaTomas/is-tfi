const API_URL = "http://localhost:8080/api/v1/pacientes";
import Cookie from 'js-cookie'


const getPacientes = async (dni) => {

    const token = Cookie.get("tokenAcceso");
    const response = await fetch(`${API_URL}`, {
        method: "GET",
        headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
        },
    });
    const data = await response.json();
    if (!response.ok) {

        throw new Error(`${data.message}`);
    }
    return data
}

const getPaciente = async (dni) => {

    const token = Cookie.get("tokenAcceso");
    const response = await fetch(`${API_URL}/${dni}`, {
        method: "GET",
        headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
        },
    });
    const data = await response.json();
    if (!response.ok) {

        throw new Error(`${data.message}`);
    }
    return data
}

const createEvolution = async (dni,diagnostico,informe) => {

    const token = Cookie.get("tokenAcceso");
    const response = await fetch(`${API_URL}/${dni}/diagnosticos/${diagnostico}/evoluciones`, {
        method: "POST",
        headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
        },
        body:JSON.stringify({
            'informe':informe,
        })
    });

    const data = await response.json();

    if (!response.ok) throw new Error(`${data.detalles.informe}`);

    return data
}

const createDiagnosis = async (dni,nombre) => {

    const token = Cookie.get("tokenAcceso");
    const response = await fetch(`${API_URL}/${dni}/diagnosticos`, {
        method: "POST",
        headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
        },
        body:JSON.stringify({
            'nombre':nombre,
        })
    });
    const data = await response.json();

    console.log(data);
    
    if (!response.ok) throw new Error(`${data.message}`) ;

    return data
}

const createLabOrder = async (dni, diagnostico, idEvolucion, texto) => {

    const token = Cookie.get("tokenAcceso");
    const response = await fetch(`${API_URL}/${dni}/diagnosticos/${diagnostico}/evoluciones/${idEvolucion}/pedidos-laboratorio`, {
        method: "POST",
        headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
        },
        body:JSON.stringify({
            'texto':texto,
        })
    });
    const data = await response.json();

    console.log(data);
    
    if (!response.ok)  throw new Error(`${data.detalles.texto}`);

    return data
}

const createPrescription = async (dni, diagnostico, idEvolucion, texto) => {

    const token = Cookie.get("tokenAcceso");
    const response = await fetch(`${API_URL}/${dni}/diagnosticos/${diagnostico}/evoluciones/${idEvolucion}/pedidos-laboratorio`, {
        method: "POST",
        headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
        },
        body:JSON.stringify({
            'texto':texto,
        })
    });
    const data = await response.body();

    console.log(data);
    
    if (!response.ok)  throw new Error(`${data.message}`);

    return data
}

export const pacienteService = {
    getPaciente,getPacientes,createEvolution,createDiagnosis,createLabOrder,createPrescription
}