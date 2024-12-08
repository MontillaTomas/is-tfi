import React, { useState } from 'react'
// import { XIcon } from '@heroicons/react/solid'

function CreatePatientModal({ isOpen, onClose }) {
  const [patientData, setPatientData] = useState({
    name: '',
    age: '',
    gender: '',
    bloodType: ''
  })

  const handleChange = (e) => {
    const { name, value } = e.target
    setPatientData(prevData => ({
      ...prevData,
      [name]: value
    }))
  }

  const handleSubmit = (e) => {
    e.preventDefault()
    // Aquí normalmente enviarías los datos a tu API
    console.log('Datos del paciente:', patientData)
    // Limpia el formulario y cierra el modal
    setPatientData({ name: '', birthdate: '', email: '' })
    onClose()
  }

  if (!isOpen) return null

  return (
    <div className="fixed inset-0 bg-gray-600 bg-opacity-50 overflow-y-auto h-full w-full" id="my-modal">
      <div className="relative top-20 mx-auto p-5 border w-96 shadow-lg rounded-md bg-white">
        <div className="mt-3 text-center">
          <h3 className="text-lg leading-6 font-medium text-gray-900">Crear Nuevo Paciente</h3>
          <div className="mt-2 px-7 py-3">
            <form onSubmit={handleSubmit}>
              <input
                type="text"
                name="name"
                value={patientData.name}
                onChange={handleChange}
                placeholder="Nombre completo"
                className="mt-1 block w-full px-3 py-2 bg-white text-black border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
                required
              />
              <input
                type="number"
                name="birthdate"
                value={patientData.birthdate}
                onChange={handleChange}
                placeholder="Fecha de Nacimiento"
                className="mt-3 block w-full px-3 py-2 bg-white border text-black border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
                required
              />
              <input
                type="text"
                name="email"
                value={patientData.email}
                onChange={handleChange}
                placeholder="Email"
                className="mt-3 block w-full px-3 py-2 text-black bg-white border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
                required
              />
              <div className="flex justify-between mt-4">
                <button
                  type="button"
                  onClick={onClose}
                  className="px-4 py-2 bg-gray-200  text-gray-800 rounded-md hover:bg-gray-300 focus:outline-none focus:ring-2 focus:ring-gray-400"
                >
                  Cancelar
                </button>
                <button
                  type="submit"
                  className="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500"
                >
                  Crear Paciente
                </button>
              </div>
            </form>
          </div>
        </div>
        <button
          onClick={onClose}
          className="absolute top-0 right-0 mt-4 mr-4 text-gray-500 hover:text-gray-800"
        >
          {/* <XIcon className="h-6 w-6" /> */}
        </button>
      </div>
    </div>
  )
}

export default CreatePatientModal

