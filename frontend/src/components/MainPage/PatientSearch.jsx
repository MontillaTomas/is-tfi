import React, { useEffect, useState } from 'react'
import usePaciente from '../../hooks/usePaciente'

function PatientSearch({ onPatientSelect, selectDiagnosis, selectEvolution }) {
  const [searchTerm, setSearchTerm] = useState('')
  const [suggestions, setSuggestions] = useState([])
  const [patients, setPatients] = useState([])
  
  const { getPacientes, getPaciente } = usePaciente() 

  useEffect(() => {
    const fetchAllPatients = async () => {
      const allPatients = await getPacientes()
      setPatients(allPatients)
    }
    fetchAllPatients()
  }, [])

  useEffect(() => {
    if (searchTerm.length > 0) {
      const filteredPatients = patients.filter(patient => 
        patient.nombre.toLowerCase().includes(searchTerm.toLowerCase()) ||
        patient.dni.toString().includes(searchTerm)
      )
      setSuggestions(filteredPatients)
    } else {
      setSuggestions([])
    }
  }, [searchTerm, patients])

  const handleSearch = async (e) => {
    e.preventDefault()
    const paciente = await getPaciente(searchTerm);
    onPatientSelect(paciente);
    selectDiagnosis(null);
    selectEvolution(null);
    setSuggestions([])
  }

  const handleSuggestionClick = async (patient) => {
    const paciente = await getPaciente(patient.dni.toString());
    onPatientSelect(paciente);
    selectDiagnosis(null);
    selectEvolution(null);
    setSearchTerm('')
    setSuggestions([])
  }

  return (
    <div className="relative">
      <form onSubmit={handleSearch} className="flex items-center">
        <input
          type="text"
          placeholder="Buscar paciente por nombre o DNI..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          className="flex-grow px-4 py-2 border border-gray-300 rounded-l-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
          aria-label="Buscar paciente"
          aria-autocomplete="list"
        />

      </form>
      {suggestions.length > 0 && (
        <ul className="absolute z-10 w-full bg-white border border-gray-300 mt-1 rounded-md shadow-lg max-h-60 overflow-y-auto" role="listbox">
          {suggestions.map((patient) => (
            <li
              key={patient.dni}
              onClick={() => handleSuggestionClick(patient)}
              className="px-4 py-2 hover:bg-gray-100 cursor-pointer"
              role="option"
            >
              {patient.nombre} - DNI: {patient.dni}
            </li>
          ))}
        </ul>
      )}
    </div>
  )
}

export default PatientSearch

