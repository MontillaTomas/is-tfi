const API_URL = "https://istp1service.azurewebsites.net/api/servicio-salud/medicamentos?descripcion=";

const getMedicines = async (descripcion) => {

    try {
        const response = await fetch(`${API_URL}${descripcion}`, {

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
    } catch (error) {
        console.error('Error fetching medicines:', error);
        throw error;
    }
   
}

export const generalsService = {
    getMedicines
}