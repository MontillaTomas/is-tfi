import React, { useState } from 'react'
import usePaciente from '../../hooks/usePaciente'

function CreateEvolutionModal({ isOpen, onClose, selectedDiagnosis, selectedPatient, setEvolutionAdded }) {
  const [evolutionData, setEvolutionData] = useState({
    description: '',
  })

  const { createEvolution } = usePaciente() 


  const handleChange = (e) => {
    const { name, value } = e.target
    setEvolutionData(prevData => ({
      ...prevData,
      [name]: value
    }))
  }

  const handleSubmit = async (e) => {
    e.preventDefault()

    const evolucion = await createEvolution(selectedPatient.dni,selectedDiagnosis, evolutionData.description);
    console.log(evolucion);

    setEvolutionData({ description: '' })
    setEvolutionAdded(true)
    onClose()
  }

  if (!isOpen) return null

  return (
    <div className="fixed inset-0 bg-gray-600 bg-opacity-50 overflow-y-auto h-full w-full" id="my-modal">
      <div className="relative top-20 mx-auto p-5 border w-96 shadow-lg rounded-md bg-white">
        <div className="mt-3 text-center">
          <h3 className="text-lg leading-6 font-medium text-gray-900">Agregar Nueva Evolución</h3>
          <div className="mt-2 px-7 py-3">
            <form onSubmit={handleSubmit}>
              <textarea
                name="description"
                value={evolutionData.description}
                onChange={handleChange}
                placeholder="Informe"
                className="mt-1 block w-full px-3 py-2 bg-white border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
                rows="4"
                required
              ></textarea>
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
                  Agregar Evolución
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

export default CreateEvolutionModal