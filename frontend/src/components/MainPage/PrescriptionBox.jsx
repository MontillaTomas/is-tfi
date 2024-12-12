import React, { useState, useEffect} from 'react'
import CreatePrescriptionModal from '../Modal/CreatePrescriptionModal'

function PrescriptionBox({ selectedEvolution, selectedPatient, setPrescriptionAdded, reloadPatientData, prescriptionAdded }) {
  const [isModalOpen, setIsModalOpen] = useState(false)
  
  useEffect(() => {
    if (prescriptionAdded) {
      reloadPatientData();
      setPrescriptionAdded(false);
    }
  }, [prescriptionAdded, reloadPatientData, setPrescriptionAdded]);


  const receta = selectedEvolution?.recetasDigitales
  .reduce((ultima, receta) => {
      const fechaReceta = new Date(receta.fechaHora);
      return !ultima || fechaReceta > new Date(ultima.fechaHora) ? receta : ultima;
  }, null);
  
  
  return (
    <div className="bg-white shadow-md rounded-lg p-6">
      <h2 className="text-2xl font-bold mb-4">Recetas Digitales</h2>
      <div className="mb-4">
        <h3 className="text-lg font-semibold">Última Receta:</h3>
        {receta  ? receta.medicamentos.map((medicamento,id)=>(
          <p key={id}>Medicamento {id+1}: {medicamento?.descripcion} - {medicamento?.formato}</p>
        ))
        :
        <p>No hay recetas</p>
        }
        {receta ? (
          <p>Fecha: {new Date(receta.fechaHora).toLocaleString()}</p>
        ):<></>}
      </div>
      <button
        onClick={() => setIsModalOpen(true)}
        className="bg-green-500 hover:bg-green-600 text-white px-4 py-2 rounded-md flex items-center"
      >
        Nueva Receta
      </button>
      <CreatePrescriptionModal 
        isOpen={isModalOpen} 
        onClose={() => setIsModalOpen(false)}  
        selectedPatient={selectedPatient?.dni}
        selectedDiagnosis={selectedEvolution?.diagnosticoNombre}
        selectedEvolution={selectedEvolution?.id}
        setPrescriptionAdded={setPrescriptionAdded}
        reloadPatientData={reloadPatientData}
      />
    </div>
  )
}

export default PrescriptionBox