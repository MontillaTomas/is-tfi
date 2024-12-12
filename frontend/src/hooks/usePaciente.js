import {useState} from 'react'
import { pacienteService } from '../service/pacienteService'
import { generalsService } from '../service/generalsService'

const usePaciente = () => {
  
    const [error,setError] = useState(null)
    const [loading,setLoading] = useState(false)
    const [possibleDiagnoses, setPossibleDiagnosis] = useState(null)

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

    const createPrescription = async (dni, diagnostico, idEvolucion, medication1, medication2) => {
        
        setLoading(true);
        setError(null)
        try {
            
            const data = await pacienteService.createPrescription(dni, diagnostico, idEvolucion, medication1, medication2);
            console.log(data);
            return data

        } catch (error) {
            throw `${error}`
        }finally{
            setLoading(false);
        }
    }

    const getDiagnosis = async () => {
        setLoading(true);
        setError(null)
        try {
          const data = await generalsService.getDiagnosis();
          setPossibleDiagnosis(data)
          return data
        } catch (error) {         
          throw `${error}`
        } finally {
          setLoading(false);
        }
      }
      

    return {
        error,
        loading,
        possibleDiagnoses,
        getPaciente,
        getPacientes, 
        createEvolution,
        createDiagnosis,
        createLabOrder,
        createPrescription,
        getDiagnosis
    }

}

export default usePaciente