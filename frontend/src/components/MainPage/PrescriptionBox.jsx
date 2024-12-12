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


  const recetas = selectedEvolution?.recetasDigitales
  .sort((a, b) => new Date(b.fechaHora) - new Date(a.fechaHora))
  
  console.log(recetas);
  
  return (
    <div className="bg-white shadow-md rounded-lg p-6">
      <h2 className="text-2xl font-bold mb-4">Recetas Digitales</h2>
      <div className="mb-4">
        <ul className="space-y-4">
          {recetas.length > 0 ? recetas.map((receta,id)=>(
            <>
            <li key={id} className={'p-2 rounded-md '} >
             <p>Receta {receta.id}</p>   
              {
              receta.medicamentos.map((medicamento)=>(
                
                  <p>{`${medicamento.descripcion} - ${medicamento.formato}`}</p>
                
                ))}
              <p>Fecha: {new Date(receta.fechaHora).toLocaleString()}</p>
            </li> 
            </>
          ))
          :
          <p>No hay recetas</p>
          }     
         
        </ul>
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