import React, { useState, useEffect } from 'react'
import CreateEvolutionModal from '../Modal/CreateEvolutionModal'
import usePaciente from '../../hooks/usePaciente'

function EvolutionList({ diagnosticos, selectedDiagnosis, selectedPatient, evolutionAdded, setEvolutionAdded  }) {

  const [isModalOpen, setIsModalOpen] = useState(false)
  const [diagnosticosState, setDiagnosticos] = useState(diagnosticos)

  const { getPaciente } = usePaciente()

  useEffect(() => {
    if (evolutionAdded) {
      const reloadPatientData = async () => {
        const updatedPatient = await getPaciente(selectedPatient.dni)
 
        setDiagnosticos(updatedPatient.historiaClinica.diagnosticos)
        setEvolutionAdded(false)
      }
      reloadPatientData()
    }
  }, [evolutionAdded, selectedPatient, getPaciente, setEvolutionAdded])

  const evoluciones = diagnosticosState
    .flatMap((diagnostico) =>
      diagnostico.evoluciones.map((evolucion) => ({
        ...evolucion,
        diagnosticoNombre: diagnostico.nombre,
      }))
    ) 
    .filter(
      (evolucion) =>
        !selectedDiagnosis || evolucion.diagnosticoNombre === selectedDiagnosis
    ) 
    .sort((a, b) => new Date(b.fechaHora) - new Date(a.fechaHora));

  return (
    <div className="bg-white shadow-md rounded-lg p-6">
      <div className="flex justify-between items-center mb-4">
        <h2 className="text-2xl font-bold">Evoluciones</h2>
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
        setEvolutionAdded={setEvolutionAdded}/>
      </div>
    <ul className="space-y-4">
      {evoluciones.map((evolucion, id) => (
        <li key={id} className="border-b pb-2">
          <p className="font-semibold">{evolucion.diagnosticoNombre}</p>
          <p>{evolucion.informe}</p>
        </li>
      ))}
    </ul>
  </div>
  )
}

export default EvolutionList

