import React, { useState, useEffect } from 'react'
import CreateEvolutionModal from '../Modal/CreateEvolutionModal'

function EvolutionList({ diagnosticos, selectedDiagnosis, selectedEvolution, selectedPatient, setEvolutionAdded, reloadPatientData, onSelectEvolution  }) {

  const [isModalOpen, setIsModalOpen] = useState(false)
  const [selectedId, setSelectedId] = useState(null);

  const handleSelect = (evolucion, id) => {

    if (selectedId!=null && id===selectedId ) {
      setSelectedId(null);
      onSelectEvolution(null)
    }else{
      onSelectEvolution(evolucion);
      setSelectedId(id);
    }
  };

  const evoluciones = diagnosticos
    .flatMap((diagnostico) =>
      diagnostico.evoluciones.map((evolucion) => ({
        ...evolucion,
        diagnosticoNombre: diagnostico.nombre,
      }))
    ) 
    .filter(
      (evolucion) =>
        !selectedDiagnosis || evolucion.diagnosticoNombre === selectedDiagnosis.nombre
    ) 
    .sort((a, b) => new Date(b.fechaHora) - new Date(a.fechaHora));

  return (
    <div className="bg-white shadow-md rounded-lg p-6">
      <div className="flex justify-between items-center mb-4">
        <h2 className="text-2xl font-bold ps-2">Evoluciones</h2>
        {selectedDiagnosis && (
          <button
          onClick={() => setIsModalOpen(true)}
          className="bg-green-500 hover:bg-green-600 text-white px-4 py-2 rounded-md flex items-center"
          >
            Agregar Evolución
          </button>
        )}
        <CreateEvolutionModal 
        isOpen={isModalOpen} 
        onClose={() => setIsModalOpen(false)} 
        selectedDiagnosis={selectedDiagnosis} 
        selectedPatient={selectedPatient}
        setEvolutionAdded={setEvolutionAdded}
        reloadPatientData={reloadPatientData}
        />
      </div>
    <ul className="space-y-4">
      {evoluciones.length > 0 ? evoluciones.map((evolucion, id) => (
        <li key={id} className={`cursor-pointer border-b p-2 rounded-md ${selectedId === id ? 'bg-blue-200': ''}`} onClick={() => handleSelect(evolucion, id)}>
          <p className="font-semibold">{evolucion.diagnosticoNombre}</p>
          <p>{evolucion.informe}</p>
        </li>
      )):
        <p className="font-semibold">No hay evoluciones realizadas</p>
      }
    </ul>
  </div>
  )
}

export default EvolutionList

