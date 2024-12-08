import React, { useState } from 'react'

function CreateLabOrderModal({ isOpen, onClose, patientId }) {
  const [labOrderData, setLabOrderData] = useState({
    test: '',
    notes: ''
  })

  const handleChange = (e) => {
    const { name, value } = e.target
    setLabOrderData(prevData => ({
      ...prevData,
      [name]: value
    }))
  }

  const handleSubmit = (e) => {
    e.preventDefault()
    // Aquí normalmente enviarías los datos a tu API
    console.log('Datos del pedido de laboratorio:', { patientId, ...labOrderData })
    // Limpia el formulario y cierra el modal
    setLabOrderData({ test: '', notes: '' })
    onClose()
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
                name="notes"
                value={labOrderData.notes}
                onChange={handleChange}
                placeholder="Descripcion"
                className="mt-3 block w-full px-3 py-2 bg-white border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
                rows="3"
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
                  Crear Pedido
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

export default CreateLabOrderModal