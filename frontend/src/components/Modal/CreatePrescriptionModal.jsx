import React, { useState, useEffect } from 'react'

let possibleMedications = [
  "Paracetamol",
  "Ibuprofeno",
  "Amoxicilina",
  "Metformina",
  "Omeprazol",
  "Aspirina",
  "Loratadina",
  "Ciprofloxacina",
  "Salbutamol",
  "Ranitidina",
  "Clonazepam",
  "Diclofenaco",
  "Atorvastatina",
  "Levotiroxina",
  "Enalapril",
  "Simvastatina",
  "Losartán",
  "Furosemida",
  "Prednisona",
  "Metronidazol",
  "Gabapentina",
  "Cetirizina",
  "Pantoprazol",
  "Meloxicam"
];

function CreatePrescriptionModal({ isOpen, onClose, patientId }) {
  const [prescriptionData, setPrescriptionData] = useState({
    medication: '',
    medication2:''
  })

  const [suggestions, setSuggestions] = useState([])
  const [suggestions2, setSuggestions2] = useState([])

  useEffect(() => {
    if (prescriptionData.medication) {
      const filteredSuggestions = possibleMedications.filter(medication =>
        medication.toLowerCase().includes(prescriptionData.medication.toLowerCase())
      )
      setSuggestions(filteredSuggestions)
    } else {
      setSuggestions([])
    }

  }, [prescriptionData.medication])

  useEffect(() => {
    if (prescriptionData.medication2) {
      const filteredSuggestions = possibleMedications.filter(medication =>
        medication.toLowerCase().includes(prescriptionData.medication2.toLowerCase())
      )
      setSuggestions2(filteredSuggestions)
    } else {
      setSuggestions2([])
    }

  }, [prescriptionData.medication2])

  const handleChange = (e) => {
    const { name, value } = e.target
    setPrescriptionData(prevData => ({
      ...prevData,
      [name]: value
    }))
  }

  const handleSuggestionClick = (suggestion) => {
    setDiagnosisData(prevData => ({
      ...prevData,
      name: suggestion
    }))
    setSuggestions([])
  }

  const handleSuggestionClick2 = (suggestion) => {
    setDiagnosisData(prevData => ({
      ...prevData,
      name: suggestion
    }))
    setSuggestions2([])
  }

  const handleSubmit = (e) => {
    e.preventDefault()

    setPrescriptionData({ medication: '', medication2:'' })
    onClose()
  }

  if (!isOpen) return null

  return (
    <div className="fixed inset-0 bg-gray-600 bg-opacity-50 overflow-y-auto h-full w-full" id="my-modal">
      <div className="relative top-20 mx-auto p-5 border w-96 shadow-lg rounded-md bg-white">
        <div className="mt-3 text-center">
          <h3 className="text-lg leading-6 font-medium text-gray-900">Crear Nueva Receta</h3>
          <div className="mt-2 px-7 py-3">
            <form onSubmit={handleSubmit}>
              <div className='relative'>
                <input
                  type="text"
                  name="medication"
                  value={prescriptionData.medication}
                  onChange={handleChange}
                  placeholder="Medicamento 1"
                  className="mt-1 block w-full px-3 py-2 bg-white border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
                  required
                />
                
                {suggestions.length > 0 && (
                  <ul className="absolute z-10 w-full bg-white border border-gray-300 rounded-md shadow-lg max-h-60 overflow-auto">
                    {suggestions.map((suggestion, index) => (
                      <li
                        key={index}
                        className="px-3 py-2 hover:bg-gray-100 cursor-pointer"
                        onClick={() => handleSuggestionClick(suggestion)}
                      >
                        {suggestion}
                      </li>
                    ))}
                  </ul>
                )}
                <input
                  type="text"
                  name="medication2"
                  value={prescriptionData.medication2}
                  onChange={handleChange}
                  placeholder="Medicamento 2"
                  className="mt-1 block w-full px-3 py-2 bg-white border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
                  required
                />
                {suggestions2.length > 0 && (
                  <ul className="absolute z-10 w-full bg-white border border-gray-300 rounded-md shadow-lg max-h-60 overflow-auto">
                    {suggestions2.map((suggestion, index) => (
                      <li
                        key={index}
                        className="px-3 py-2 hover:bg-gray-100 cursor-pointer"
                        onClick={() => handleSuggestionClick2(suggestion)}
                      >
                        {suggestion}
                      </li>
                    ))}
                  </ul>
                )}
              </div>
              <div className="flex justify-between mt-4">
                <button
                  type="button"
                  onClick={onClose}
                  className="px-4 py-2 bg-gray-200 text-gray-800 rounded-md hover:bg-gray-300 focus:outline-none focus:ring-2 focus:ring-gray-400"
                >
                  Cancelar
                </button>
                <button
                  type="submit"
                  className="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500"
                >
                  Crear Receta
                </button>
              </div>
            </form>
          </div>
        </div>
        <button
          onClick={onClose}
          className="absolute top-0 right-0 mt-4 mr-4 text-gray-500 hover:text-gray-800"
        >
        </button>
      </div>
    </div>
  )
}

export default CreatePrescriptionModal
