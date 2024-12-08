import {useState} from 'react'
import { pacienteService } from '../service/pacienteService'

const usePaciente = () => {
  
    const [error,setError] = useState(null)
    const [loading,setLoading] = useState(false)

    const getAllPacientes = async() =>{
        setLoading(true);
        setError(null)
        try {
            const data = await pacienteService.getAllPacientes()
        } catch (error) {
            
        }
    }


    const getPaciente = async(dni)=>{
        setLoading(true);
        setError(null)
        try {
            
            const data = await pacienteService.getPaciente(dni);
            console.log(data);
            
            return data

        } catch (error) {
            setError(error.message);
        }finally{
            setLoading(false);
        }
    }

    const createEvolution = async(dni, diagnostico, informe)=>{
        setLoading(true);
        setError(null)
        try {
            
            const data = await pacienteService.createEvolution(dni, diagnostico, informe );
            await getPaciente(dni)
            console.log(data);
            return data

        } catch (error) {
            setError(error.message);
        }finally{
            setLoading(false);
        }
    }



    return {
        error,
        loading,
        getPaciente, 
        createEvolution
    }

}

export default usePaciente