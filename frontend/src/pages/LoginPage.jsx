import React, { useState } from 'react'
import { useAuth } from '../context/AuthContext'
import InputField from '../components/LoginPage.jsx/InputField'
import PasswordField from '../components/LoginPage.jsx/PasswordField'

function LoginPage() {
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const [showPassword, setShowPassword] = useState(false)
  const [authError, setAuthError] = useState(null); 

  const auth = useAuth()
  const handleSubmit = async (e) => {
    e.preventDefault();
    try{
      await auth.login({email: username,contrasena: password});
    }catch(error){
      let err = await error;
      console.log(err);
      
      // setAuthError(error.data.error);
    } 
  }

  return (
    <main className='bg-gray-100 flex items-center justify-center min-h-screen'>
      <div className="bg-white p-8 rounded-lg shadow-md w-full max-w-md ">
            <h1 className="text-2xl font-bold mb-6 text-center text-blue-600">Gestión de Clínica</h1>
            <form onSubmit={handleSubmit} className="space-y-4">
                <InputField
                  label="Usuario"
                  id="username"
                  type="text"
                  value={username}
                  onChange={(e) => setUsername(e.target.value)}
                  required
                />
              <PasswordField
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                showPassword={showPassword}
                toggleShowPassword={() => setShowPassword(!showPassword)}
              />
              <div>
                <button type="submit" className="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500">
                  Iniciar sesión
                </button>
              </div>
          </form>
      </div>
    </main>
    
  )
}

export default LoginPage

