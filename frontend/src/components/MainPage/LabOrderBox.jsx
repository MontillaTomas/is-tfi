import React, { useState } from 'react'
import CreateLabOrderModal from '../Modal/CreateLabOrderModal'

function LabOrderBox({ selectedEvolution }) {
  const [isModalOpen, setIsModalOpen] = useState(false)
  
  const pedido = selectedEvolution.pedidosLaboratorio
  .reduce((recetaIdMasAlto, receta) => {
      return !recetaIdMasAlto || receta.id > recetaIdMasAlto ? receta : recetaIdMasAlto;
  }, null);
  

  return (
    <div className="bg-white shadow-md rounded-lg p-6">
      <h2 className="text-2xl font-bold mb-4">Pedidos de Laboratorio</h2>
      <div className="mb-4">
        <h3 className="text-lg font-semibold">Último Pedido:</h3>
        {pedido ? <p>Descripcion: {pedido.texto}</p> : <p>No hay pedidos de laboratorio </p> }
      </div>
      <button
        onClick={() => setIsModalOpen(true)}
        className="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded-md flex items-center"
      >
        Nuevo Pedido
      </button>
      <CreateLabOrderModal isOpen={isModalOpen} onClose={() => setIsModalOpen(false)} />
    </div>
  )
}

export default LabOrderBox
