import React, { useState } from 'react'
// import { SearchIcon, UserAddIcon, LogoutIcon } from '@heroicons/react/solid'
import { useAuth } from '../context/AuthContext';
import CreatePatientModal from './Modal/CreatePatientModal'

function Navbar() {
  const [isModalOpen, setIsModalOpen] = useState(false)
  const auth = useAuth();

  const handleLogout = () => {
    if (window.confirm("¿Estás seguro de que deseas cerrar sesión?")) {
      auth.logout();
    }
};

  return (
    <nav className="bg-blue-600 text-white shadow-lg">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 xl:px-6 2xl:px-0">
        <div className="flex items-center justify-between h-16">
          <div className="flex-shrink-0">
            <h1 className="text-2xl font-bold">Clínica Salud</h1>
          </div>
          <div className="flex items-center">
            <button 
              className="bg-blue-700 hover:bg-blue-800 px-3 py-2 rounded-md text-sm font-medium flex items-center mr-4"
              onClick={() => setIsModalOpen(true)}
            >
              {/* <UserAddIcon className="h-5 w-5 mr-2" /> */}
              Crear Paciente
            </button>
            <button onClick={handleLogout} className="bg-red-600 hover:bg-red-700 px-3 py-2 rounded-md text-sm font-medium flex items-center">
              {/* <LogoutIcon className="h-5 w-5 mr-2" /> */}
              Cerrar Sesión
            </button>
          </div>
        </div>
      </div>
      <CreatePatientModal isOpen={isModalOpen} onClose={() => setIsModalOpen(false)} />
    </nav>
  )
}

export default Navbar

