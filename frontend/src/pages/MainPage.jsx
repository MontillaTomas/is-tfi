import React, { useState, useCallback, useEffect  } from 'react'
import Navbar from '../components/Navbar'
import PatientSearch from '../components/MainPage/PatientSearch'
import PatientInfo from '../components/MainPage/PatientInfo'
import DiagnosisList from '../components/MainPage/DiagnosisList'
import EvolutionList from '../components/MainPage/EvolutionList'
import LabOrderBox from '../components/MainPage/LabOrderBox'
import PrescriptionBox from '../components/MainPage/PrescriptionBox'
import usePaciente from '../hooks/usePaciente'

function MainPage() {
  const [selectedPatient, setSelectedPatient] = useState(null)
  const [selectedDiagnosis, setSelectedDiagnosis] = useState(null);
  const [selectedEvolution, setSelectedEvolution] = useState(null);
  const [evolutionAdded, setEvolutionAdded] = useState(false);
  const [diagnosisAdded, setDiagnosisAdded] = useState(false);
  const [labOrderAdded, setLabOrderAdded] = useState(false);
  const [prescriptionAdded, setPrescriptionAdded] = useState(false);

  const { getPaciente } = usePaciente()

   const handlePatientSelect = useCallback(async (patient) => {
    const updatedPatient = await getPaciente(patient.dni)
    setSelectedPatient(updatedPatient)
    setSelectedDiagnosis(null)
  }, [getPaciente])

  const reloadPatientData = useCallback(async () => {
    if (selectedPatient) {
      const updatedPatient = await getPaciente(selectedPatient.dni);
      setSelectedPatient(updatedPatient);
      
      if (selectedEvolution) {
        const updatedDiagnosis = updatedPatient.historiaClinica.diagnosticos
          .find(d => d.nombre === selectedEvolution.diagnosticoNombre);
      
        if (updatedDiagnosis) {
          const updatedEvolution = updatedDiagnosis.evoluciones
            .find(e => e.id === selectedEvolution.id);
        
          if (updatedEvolution) {
            setSelectedEvolution({
              ...updatedEvolution,
              diagnosticoNombre: updatedDiagnosis.nombre 
            });
          }
        }
      }
    }
  }, [selectedPatient, selectedEvolution, getPaciente]);

  return (
    <div className="min-h-screen bg-gray-100">
      <div className="flex flex-col min-h-screen">
        <Navbar />
        <main className="flex-grow container mx-auto px-10 py-8">
          <PatientSearch onPatientSelect={handlePatientSelect} selectDiagnosis={setSelectedDiagnosis} selectEvolution={setSelectedEvolution}/>
          {selectedPatient && (
            <div className="mt-8">
              <PatientInfo patient={selectedPatient} />
              <div className="mt-8 grid grid-cols-1 md:grid-cols-2 gap-8">
                <DiagnosisList 
                  diagnosticos={selectedPatient.historiaClinica.diagnosticos} 
                  selectedDiagnosis={selectedDiagnosis}
                  onSelectDiagnosis={setSelectedDiagnosis}
                  onSelectEvolution={setSelectedEvolution}
                  selectedPatient={selectedPatient} 
                  setDiagnosisAdded={setDiagnosisAdded}
                  reloadPatientData={reloadPatientData}
                  />
                <EvolutionList 
                  diagnosticos={selectedPatient.historiaClinica.diagnosticos} 
                  selectedDiagnosis={selectedDiagnosis} 
                  selectedEvolution={selectedEvolution}
                  selectedPatient={selectedPatient} 
                  setEvolutionAdded={setEvolutionAdded}
                  reloadPatientData={reloadPatientData}
                  onSelectEvolution={setSelectedEvolution}
                />
              </div>
              {selectedEvolution && (
                <div className="mt-8 grid grid-cols-1 md:grid-cols-2 gap-8">
                  <PrescriptionBox    
                    selectedEvolution={selectedEvolution}
                    selectedPatient={selectedPatient} 
                    setPrescriptionAdded={setPrescriptionAdded}
                    reloadPatientData={reloadPatientData}
                    prescriptionAdded={prescriptionAdded}
                  />
                  <LabOrderBox 
                    selectedEvolution={selectedEvolution}
                    selectedPatient={selectedPatient} 
                    setLabOrderAdded={setLabOrderAdded}
                    reloadPatientData={reloadPatientData}
                    labOrderAdded={labOrderAdded}
                  />
                </div>
              )}
            </div>
          )}
        </main>
      </div>
    </div>
    
  )
}

export default MainPage

