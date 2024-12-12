import {useState} from 'react'
import { pacienteService } from '../service/pacienteService'

const usePaciente = () => {
  
    const [error,setError] = useState(null)
    const [loading,setLoading] = useState(false)

    const getPacientes = async (searchTerm) => {
        setLoading(true);
        setError(null)
        try {
          const data = await pacienteService.getPacientes(searchTerm);
          return data
        } catch (error) {
            console.log(error);
            
          setError(error);
        } finally {
          setLoading(false);
        }
      }
      
    const getPaciente = async(dni)=>{
        setLoading(true);
        setError(null)
        try {
            
            const data = await pacienteService.getPaciente(dni);
            
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
            console.log(data);
            return data

        } catch (error) {
            throw `${error}`
        }finally{
            setLoading(false);
        }
    }

    const createDiagnosis = async(dni, nombre)=>{
        setLoading(true);
        setError(null)
        try {
            
            const data = await pacienteService.createDiagnosis(dni, nombre);
            console.log(data);
            return data

        } catch (error) {     
           throw `${error}`
        }finally{
            setLoading(false);
        }
    }

    const createLabOrder = async (dni, diagnostico, idEvolucion, texto) => {
        console.log(dni,diagnostico,idEvolucion,texto);
        
        setLoading(true);
        setError(null)
        try {
            
            const data = await pacienteService.createLabOrder(dni, diagnostico, idEvolucion, texto);
            console.log(data);
            return data

        } catch (error) {
            throw `${error}`
        }finally{
            setLoading(false);
        }
    }

    return {
        error,
        loading,
        getPaciente,
        getPacientes, 
        createEvolution,
        createDiagnosis,
        createLabOrder
    }

}

export default usePaciente