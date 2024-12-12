import React, {useState, useEffect} from 'react'
import CreateDiagnosisModal from '../Modal/CreateDiagnosisModal'

function DiagnosisList({ diagnosticos, selectedDiagnosis, onSelectDiagnosis, onSelectEvolution, selectedPatient, setDiagnosisAdded, reloadPatientData}) {

  const [isModalOpen, setIsModalOpen] = useState(false)
  const [selectedId, setSelectedId] = useState(null);

  const handleSelect = (diagnosis, id) => {
    
    if (selectedId!=null &&  id===selectedId) {
      setSelectedId(null);
      onSelectDiagnosis(null)

    }else{
      setSelectedId(id);
      onSelectDiagnosis(diagnosis);
      onSelectEvolution(null)
    }
  };

  return (
    <div className="bg-white shadow-md rounded-lg p-6">
      <div className="flex justify-between items-center mb-4">
        <h2 className="text-2xl font-bold ps-2">Diagnósticos</h2>
        <button
          onClick={() => setIsModalOpen(true)}
          className="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded-md flex items-center"
        >
          Agregar Diagnóstico
        </button>
        <CreateDiagnosisModal 
          isOpen={isModalOpen} 
          onClose={() => setIsModalOpen(false)} 
          selectedPatient={selectedPatient}
          setDiagnosisAdded={setDiagnosisAdded}
          reloadPatientData={reloadPatientData}
        />
      </div>
      <ul className="space-y-4">
        {diagnosticos.length > 0 ? diagnosticos.map((diagnosis, id) => (
          <li key={id} className={`border-b p-2 cursor-pointer rounded-md ${selectedId == id ? 'bg-blue-200' : ''}`} onClick={() => handleSelect(diagnosis,id)}>
            <p className="font-semibold">{diagnosis.nombre}</p>
          </li>
        )): (
          <p className="font-semibold">No hay diagnosticos</p>
        )}
        {diagnosticos.length > 0 && selectedDiagnosis!=null ? (
          <li className="border-b p-2 cursor-pointer hover:bg-gray-100" onClick={() => handleSelect(null)} >
            <p className="font-semibold text-gray-500 italic">Mostrar todas las evoluciones</p>
          </li>
        ): <></>}    
      </ul>
    </div>
  )
}

export default DiagnosisList

