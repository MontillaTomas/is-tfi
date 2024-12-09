import React, {useState, useEffect} from 'react'
import CreateDiagnosisModal from '../Modal/CreateDiagnosisModal'
import usePaciente from '../../hooks/usePaciente'

function DiagnosisList({ diagnosticos, onSelectDiagnosis, selectedPatient, diagnosisAdded ,setDiagnosisAdded}) {

  const [isModalOpen, setIsModalOpen] = useState(false)
  const [diagnosticosState, setDiagnosticos] = useState(diagnosticos)

  const { getPaciente } = usePaciente()

  useEffect(() => {
    if (diagnosisAdded) {
      const reloadPatientData = async () => {
        const updatedPatient = await getPaciente(selectedPatient.dni)
 
        setDiagnosticos(updatedPatient.historiaClinica.diagnosticos)
        setDiagnosisAdded(false)
      }
      reloadPatientData()
    }
  }, [diagnosisAdded, selectedPatient, getPaciente, setDiagnosisAdded])

  return (
    <div className="bg-white shadow-md rounded-lg p-6">
      <div className="flex justify-between items-center mb-4">
        <h2 className="text-2xl font-bold">Diagnósticos</h2>
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
        />
      </div>
      <ul className="space-y-4">
        {diagnosticosState.map((diagnosis, id) => (
          <li key={id} className="border-b pb-2 cursor-pointer" onClick={() => onSelectDiagnosis(diagnosis.nombre)}>
            <p className="font-semibold">{diagnosis.nombre}</p>
          </li>
        ))}
        <li
          className="border-b pb-2 cursor-pointer hover:bg-gray-100"
          onClick={() => onSelectDiagnosis(null)} 
        >
          <p className="font-semibold text-gray-500 italic">Mostrar todos</p>
        </li>
      </ul>
    </div>
  )
}

export default DiagnosisList

