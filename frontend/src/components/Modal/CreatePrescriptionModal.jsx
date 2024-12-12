import React, { useState } from 'react'
import usePaciente from '../../hooks/usePaciente'

const medications = [
  {
    "codigo": 10045,
    "descripcion": "CARBOPLATINO MONTEDISON",
    "formato": "450 mg f.a x 1"
  },
  {
    "codigo": 10043,
    "descripcion": "CARBOPLATINO MONTEDISON",
    "formato": "50 mg f.a.x 1"
  },
  {
    "codigo": 21295,
    "descripcion": "CARBOPLATINO PLATINWAS",
    "formato": "50 mg iny.f.a.x 1"
  },
  {
    "codigo": 21296,
    "descripcion": "CARBOPLATINO PLATINWAS",
    "formato": "150 mg iny.f.a.x 1"
  },
  {
    "codigo": 21297,
    "descripcion": "CARBOPLATINO PLATINWAS",
    "formato": "450 mg iny.f.a.x 1"
  },
  {
    "codigo": 5791,
    "descripcion": "CARBOPLATINO RAFFO",
    "formato": "150 mg iny.liof.f.a.x 1"
  },
  {
    "codigo": 12546,
    "descripcion": "CARBOPLATINO TUTEUR",
    "formato": "50 mg iny.liof.f.a.x 1"
  },
]

function CreatePrescriptionModal({ isOpen, onClose, selectedDiagnosis,selectedEvolution, selectedPatient, setPrescriptionAdded, reloadPatientData }) {
  const [prescriptionData, setPrescriptionData] = useState({
    medication1: { codigo: '', descripcion: '', formato: '' },
    medication2: { codigo: '', descripcion: '', formato: '' }
  })
  const [suggestions1, setSuggestions1] = useState([])
  const [suggestions2, setSuggestions2] = useState([])
  const [error,setError] = useState(null)
  const { createPrescription } = usePaciente()

  const handleChange = (e, medicationNumber) => {
    const { value } = e.target
    const filteredSuggestions = medications.filter(med => 
      med.descripcion.toLowerCase().includes(value.toLowerCase())
    )
    if (medicationNumber === 1) {
      setSuggestions1(filteredSuggestions)
    } else {
      setSuggestions2(filteredSuggestions)
    }
    setPrescriptionData(prevData => ({
      ...prevData,
      [`medication${medicationNumber}`]: { codigo: '', descripcion: value, formato: '', displayValue: ''  }
    }))
  }

  const handleSelectMedication = (medication, medicationNumber) => {
    setPrescriptionData(prevData => ({
      ...prevData,
      [`medication${medicationNumber}`]: {
        codigo: medication.codigo,
        descripcion: medication.descripcion,
        formato: medication.formato,
        displayValue: `${medication.descripcion} - ${medication.formato}`
      }
    }))
    if (medicationNumber === 1) {
      setSuggestions1([])
    } else {
      setSuggestions2([])
    }
  }
  const handleSubmit = async (e) => {
    
    e.preventDefault()

    try {
      const pedido = await createPrescription(selectedPatient,selectedDiagnosis,selectedEvolution,
        prescriptionData.medication1, prescriptionData.medication2);
      console.log(pedido);

      setPrescriptionData({
        medication1: { codigo: '', descripcion: '', formato: '', displayValue: '' },
        medication2: { codigo: '', descripcion: '', formato: '', displayValue: '' }
      })
      setPrescriptionAdded(true)
      await reloadPatientData() 
      setError(null)
      onClose()
    } catch (error) {
      setError(error)
    }
  }

  if (!isOpen) return null

  return (
    <div className="fixed inset-0 bg-gray-600 bg-opacity-50 overflow-y-auto h-full w-full" id="my-modal">
      <div className="relative top-20 mx-auto p-5 border w-96 shadow-lg rounded-md bg-white">
        <div className="mt-3 text-center">
          <h3 className="text-lg leading-6 font-medium text-gray-900">Crear Nueva Receta</h3>
          <div className="mt-2 px-7 py-3">
            <form onSubmit={handleSubmit}>
              {[1, 2].map((num) => (
                <div key={num} className="mb-4">
                  <label htmlFor={`medication${num}`} className="block text-sm font-medium text-gray-700">
                    Medicamento {num}
                  </label>
                  <input
                    type="text"
                    id={`medication${num}`}
                    value={prescriptionData[`medication${num}`].displayValue || prescriptionData[`medication${num}`].descripcion}
                    onChange={(e) => handleChange(e, num)}
                    placeholder={`Medicamento ${num}`}
                    className="text-sm mt-1 block w-full px-3 py-2 bg-white border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
                  />
                  {(num === 1 ? suggestions1 : suggestions2).length > 0 && (
                    <ul className="mt-1 bg-white border border-gray-300 rounded-md shadow-sm">
                      {(num === 1 ? suggestions1 : suggestions2).map((med) => (
                        <li 
                          key={med.codigo}
                          className="px-3 py-2 hover:bg-gray-100 cursor-pointer"
                          onClick={() => handleSelectMedication(med, num)}
                        >
                          {med.codigo} - {med.descripcion} - {med.formato}
                        </li>
                      ))}
                    </ul>
                  )}
                </div>
              ))}
              <div className="flex justify-between mt-4">
                <button
                  type="button"
                  onClick={onClose}
                  className="px-4 py-2 bg-gray-200 text-gray-800 rounded-md hover:bg-gray-300 focus:outline-none focus:ring-2 focus:ring-gray-400"
                >
                  Cancelar
                </button>
                <button
                  type="submit"
                  className="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500"
                >
                  Crear Receta
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  )
}

export default CreatePrescriptionModal