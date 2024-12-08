import React, { useEffect, useState } from 'react'
import usePaciente from '../../hooks/usePaciente'

function PatientSearch({ onPatientSelect, clearSelectDiagnosis }) {
  const [searchTerm, setSearchTerm] = useState('')

  const { getPaciente } = usePaciente() 

  
  const handleSearch = async (e) => {
    e.preventDefault()

    const paciente = await getPaciente(searchTerm);
    console.log(paciente);
    
    onPatientSelect(paciente)
    clearSelectDiagnosis(null)
  }

  return (
    <form onSubmit={handleSearch} className="flex items-center">
      <input
        type="text"
        placeholder="Buscar paciente..."
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
        className="flex-grow px-4 py-2 border border-gray-300 rounded-l-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
      />
      <button
        type="submit"
        className="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-r-md flex items-center"
      >
        {/* <SearchIcon className="h-5 w-5 mr-2" /> */}
        Buscar
      </button>
    </form>
  )
}

export default PatientSearch

