import {createContext, useContext, useEffect, useState} from 'react'
import { authService } from '../service/authService.js';
import {jwtDecode} from "jwt-decode"
import Cookie from 'js-cookie'

const authContext = createContext(null);


export const AuthContext = ({children}) => {
   
    const [user,setUser] = useState(null);
    const [isAuthenticated,setIsAuthenticated] = useState(false); 

    useEffect(()=>{

      async function verificarSesion(){
          const jwt = Cookie.get("tokenAcceso");
          if(!jwt){
              setIsAuthenticated(false);
              setUser(null);
          }
    
          const user = jwtDecode(jwt);
          
          if(user.sub){
            if(user.exp * 1000 < new Date().getTime()){
                setIsAuthenticated(false);
                setUser(null);
            }else{
                setUser(user);
                setIsAuthenticated(true);
            }
        }
      }
    
      verificarSesion();
    
    },[])
    
    
    const login = async (user)=>{
        try {
            const userData = await authService.login(user);
            if(userData){
                setUser(userData);
                setIsAuthenticated(true);
            } 
        } catch (error) {
            throw error;
        }
    }

    const logout = () =>{
        setIsAuthenticated(false);
        setUser(null);
        Cookie.remove('tokenAcceso');
    }

  return (
    <authContext.Provider value={{user,isAuthenticated,login, logout}}>
        {children}
    </authContext.Provider>
  )
}

export const useAuth = () => {
    const context = useContext(authContext);
    if (context === undefined) {
      throw new Error('useAuth debe ser usado dentro de un AuthContext');
    }
    return context;
  };