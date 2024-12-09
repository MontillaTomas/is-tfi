import React, { useState, useCallback  } from 'react'
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
  const [evolutionAdded, setEvolutionAdded] = useState(false);
  const [diagnosisAdded, setDiagnosisAdded] = useState(false);
  const { getPaciente } = usePaciente()

   const handlePatientSelect = useCallback(async (patient) => {
    const updatedPatient = await getPaciente(patient.dni)
    setSelectedPatient(updatedPatient)
    setSelectedDiagnosis(null)
  }, [getPaciente])

  const reloadPatientData = useCallback(async () => {
    if (selectedPatient) {
      const updatedPatient = await getPaciente(selectedPatient.dni)
      setSelectedPatient(updatedPatient)
    }
  }, [selectedPatient, getPaciente])

  return (
    <div className="min-h-screen bg-gray-100">
      <div className="flex flex-col min-h-screen">
        <Navbar />
        <main className="flex-grow container mx-auto px-4 py-8">
          <PatientSearch onPatientSelect={handlePatientSelect} clearSelectDiagnosis={setSelectedDiagnosis}/>
          {selectedPatient && (
            <div className="mt-8">
              <PatientInfo patient={selectedPatient} />
              <div className="mt-8 grid grid-cols-1 md:grid-cols-2 gap-8">
                <DiagnosisList 
                  diagnosticos={selectedPatient.historiaClinica.diagnosticos} 
                  onSelectDiagnosis={setSelectedDiagnosis}
                  selectedPatient={selectedPatient} 
                  diagnosisAdded={diagnosisAdded}
                  setDiagnosisAdded={setDiagnosisAdded}
                  reloadPatientData={reloadPatientData}
                  />
                <EvolutionList 
                  diagnosticos={selectedPatient.historiaClinica.diagnosticos} 
                  selectedDiagnosis={selectedDiagnosis} 
                  selectedPatient={selectedPatient} 
                  evolutionAdded={evolutionAdded}
                  setEvolutionAdded={setEvolutionAdded}
                  reloadPatientData={reloadPatientData}
                />
              </div>
              <div className="mt-8 grid grid-cols-1 md:grid-cols-2 gap-8">
                <PrescriptionBox patientId={selectedPatient} />
                <LabOrderBox patientId={selectedPatient} />
              </div>
            </div>
          )}
        </main>
      </div>
    </div>
    
  )
}

export default MainPage

