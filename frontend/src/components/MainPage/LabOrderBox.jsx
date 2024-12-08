import React, { useState } from 'react'
import CreateLabOrderModal from '../Modal/CreateLabOrderModal'

function LabOrderBox({ patientId }) {
  const [isModalOpen, setIsModalOpen] = useState(false)
  // Aquí normalmente obtendrías el último pedido de laboratorio de una API
  const lastLabOrder = {
    id: 1,
    date: '2023-05-14',
    test: 'Hemograma completo',
  }

  return (
    <div className="bg-white shadow-md rounded-lg p-6">
      <h2 className="text-2xl font-bold mb-4">Pedidos de Laboratorio</h2>
      <div className="mb-4">
        <h3 className="text-lg font-semibold">Último Pedido:</h3>
        <p>Fecha: {lastLabOrder.date}</p>
        <p>Descripcion: {lastLabOrder.test}</p>
      </div>
      <button
        onClick={() => setIsModalOpen(true)}
        className="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded-md flex items-center"
      >
        Nuevo Pedido
      </button>
      <CreateLabOrderModal isOpen={isModalOpen} onClose={() => setIsModalOpen(false)} patientId={patientId} />
    </div>
  )
}

export default LabOrderBox
