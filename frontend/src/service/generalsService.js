const API_URL = "https://cors-anywhere.herokuapp.com/https://istp1service.azurewebsites.net/api/servicio-salud/medicamentos";
import Cookie from 'js-cookie'

const getMedicines = async () => {

        const response = await fetch(`${API_URL}/todos?pagina=10&limite=800`, {

            method: 'GET',
            headers: {
                'accept': '*/*',
            },
        });

        console.log(response);
    
        
        const data = await response.json();
        console.log(data);

        if (!response.ok)  throw new Error(`HTTP error! status: ${response.status}`);
        
        return data
   
   
}

const getMedicine = async (codigo) => {

        const response = await fetch(`${API_URL}/${codigo}`, {

            method: 'GET',
            headers: {
                "Content-Type": "application/json",
                "Access-Control-Allow-Origin": "*"
            },
            mode: 'no-cors', 
            cache: 'default'
        });
        
        
        const data = await response.json();
        console.log(data);

        if (!response.ok)  throw new Error(`HTTP error! status: ${response.status}`);
        
        return data

   
}

const getDiagnosis = async () => {

    const token = Cookie.get("tokenAcceso");
    const response = await fetch(`http://localhost:8080/api/v1/diagnosticos`, {
        method: "GET",
        headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
        },
    });

    const data = await response.json();

    console.log(data);
    
    if (!response.ok) {

        throw new Error(`${data.message}`);
    }
    return data


}


export const generalsService = {
    getMedicines, getMedicine, getDiagnosis
}