import React, { useState, useEffect} from 'react'
import CreateLabOrderModal from '../Modal/CreateLabOrderModal'

function LabOrderBox({ selectedEvolution, selectedPatient, setLabOrderAdded, reloadPatientData, labOrderAdded }) {
  const [isModalOpen, setIsModalOpen] = useState(false)
  
  useEffect(() => {
    if (labOrderAdded) {
      reloadPatientData();
      setLabOrderAdded(false);
    }
  }, [labOrderAdded, reloadPatientData, setLabOrderAdded]);

  const lastOrder = selectedEvolution?.pedidosLaboratorio?.length > 0
    ? selectedEvolution.pedidosLaboratorio[selectedEvolution.pedidosLaboratorio.length - 1]
    : null;
  
  return (
    <div className="bg-white shadow-md rounded-lg p-6">
      <h2 className="text-2xl font-bold mb-4">Pedidos de Laboratorio</h2>
      <div className="mb-4">
        <h3 className="text-lg font-semibold">Último Pedido:</h3>
        {lastOrder ? <p>Descripcion: {lastOrder.texto}</p> : <p>No hay pedidos de laboratorio </p> }
      </div>
      <button
        onClick={() => setIsModalOpen(true)}
        className="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded-md flex items-center"
      >
        Nuevo Pedido
      </button>
      <CreateLabOrderModal 
      isOpen={isModalOpen} 
      onClose={() => setIsModalOpen(false)} 
      selectedPatient={selectedPatient?.dni}
      selectedDiagnosis={selectedEvolution?.diagnosticoNombre}
      selectedEvolution={selectedEvolution?.id}
      setLabOrderAdded={setLabOrderAdded}
      reloadPatientData={reloadPatientData}
      />
    </div>
  )
}

export default LabOrderBox
