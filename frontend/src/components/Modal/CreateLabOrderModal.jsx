import React, { useState } from 'react'
import usePaciente from '../../hooks/usePaciente'

function CreateLabOrderModal({ isOpen, onClose, selectedDiagnosis,selectedEvolution, selectedPatient, setLabOrderAdded, reloadPatientData }) {
  const [labOrderData, setLabOrderData] = useState({
    text: '',
  })

  const [error,setError] = useState(null)
  const { createLabOrder } = usePaciente() 

  const handleChange = (e) => {
    const { name, value } = e.target
    setLabOrderData(prevData => ({
      ...prevData,
      [name]: value
    }))
  }
  
  const handleOnClose = ()=>{
    setError(null)
    onClose()
  }

  const handleSubmit = async(e) => {
    e.preventDefault()

    try {
      const pedido = await createLabOrder(selectedPatient,selectedDiagnosis,selectedEvolution, labOrderData.text);
      console.log(pedido);

      setLabOrderData({ text: '' })
      setLabOrderAdded(true)
      await reloadPatientData()
      setError(null)
      onClose()
    } catch (error) {
      setError(error)
    }
  }

  if (!isOpen) return null

  return (
    <div className="fixed inset-0 bg-gray-600 bg-opacity-50 overflow-y-auto h-full w-full" id="my-modal">
      <div className="relative top-20 mx-auto p-5 border w-96 shadow-lg rounded-md bg-white">
        <div className="mt-3 text-center">
          <h3 className="text-lg leading-6 font-medium text-gray-900">Crear Nuevo Pedido de Laboratorio</h3>
          <div className="mt-2 px-7 py-3">
            <form onSubmit={handleSubmit}>
              <textarea
                name="text"
                value={labOrderData.text}
                onChange={handleChange}
                placeholder="Descripcion"
                className="mt-3 block w-full px-3 py-2 bg-white border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
                rows="3"
                required
              ></textarea>
              <div className="flex justify-between mt-4">
                <button
                  type="button"
                  onClick={handleOnClose}
                  className="px-4 py-2 bg-gray-200 text-gray-800 rounded-md hover:bg-gray-300 focus:outline-none focus:ring-2 focus:ring-gray-400"
                >
                  Cancelar
                </button>
                <button
                  type="submit"
                  className="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500"
                >
                  Crear Pedido
                </button>
              </div>
              {error && 
                <div className='flex gap-3 justify-center mt-6'>
                  <svg className="w-6 h-6 text-red-700" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="none" viewBox="0 0 24 24">
                    <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 13V8m0 8h.01M21 12a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z" />
                  </svg>
                  <p className='text-red-700 text-md'>{error}</p>
                </div>}
            </form>
          </div>
        </div>
      </div>
    </div>
  )
}

export default CreateLabOrderModal