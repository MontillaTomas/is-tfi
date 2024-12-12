import {useState} from 'react'
import { generalsService } from '../service/generalsService'

const useGenerals = () => {
  
    const [error,setError] = useState(null)
    const [loading,setLoading] = useState(false)
    const [medicines, setMedicines] = useState(null)

    const getMedicines = async()=>{
        setLoading(true);
        setError(null)
        try {
            
            const data = await generalsService.getMedicines();
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
        getMedicines
    }
}

export default useGenerals