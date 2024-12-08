import React, { useState } from 'react'
import CreatePrescriptionModal from '../Modal/CreatePrescriptionModal'

function PrescriptionBox({ patientId }) {
  const [isModalOpen, setIsModalOpen] = useState(false)
  // Aquí normalmente obtendrías la última receta de una API
  const lastPrescription = {
    id: 1,
    date: '2023-05-15',
    medication: 'Paracetamol 500mg',
  }

  return (
    <div className="bg-white shadow-md rounded-lg p-6">
      <h2 className="text-2xl font-bold mb-4">Recetas Digitales</h2>
      <div className="mb-4">
        <h3 className="text-lg font-semibold">Última Receta:</h3>
        <p>Fecha: {lastPrescription.date}</p>
        <p>Medicamentos: {lastPrescription.medication}</p>
      </div>
      <button
        onClick={() => setIsModalOpen(true)}
        className="bg-green-500 hover:bg-green-600 text-white px-4 py-2 rounded-md flex items-center"
      >
        Nueva Receta
      </button>
      <CreatePrescriptionModal isOpen={isModalOpen} onClose={() => setIsModalOpen(false)} patientId={patientId} />
    </div>
  )
}

export default PrescriptionBox