import React, { useState, useEffect, useRef } from 'react'
import useGenerals from "../../hooks/useGenerals";


function CreatePrescriptionModal({ isOpen, onClose,  }) {
  const [selections, setSelections] = useState([
    { description: '', format: '', medicine: null },
    { description: '', format: '', medicine: null }
  ]);
  const [suggestions, setSuggestions] = useState([]);
  const [showSuggestions, setShowSuggestions] = useState([false, false]);
  const [availableFormats, setAvailableFormats] = useState([[], []]);
  const suggestionsRefs = [useRef(null), useRef(null)];
  const formatsRefs = [useRef(null), useRef(null)];

  const { getMedicines, loading } = useGenerals()

  const updateSelection = (index, update) => {
    setSelections(prev => {
      const newSelections = [...prev];
      newSelections[index] = { ...newSelections[index], ...update };
      return newSelections;
    });
  };

  const handleDescriptionChange = async (e, index) => {
    const value = e.target.value;
    updateSelection(index, { description: value, format: '', medicine: null });
    setShowSuggestions(prev => {
      const newShow = [...prev];
      newShow[index] = value.length >= 3;
      return newShow;
    });
console.log(loading);

    if (value.length >= 3 && !loading) {

        const fetchedMedicines = await getMedicines(value);
        const uniqueDescriptions = Array.from(
          new Set(fetchedMedicines.map(m => m.descripcion))
        );
        setSuggestions(
          uniqueDescriptions.map(desc =>
            fetchedMedicines.find(m => m.descripcion === desc)
          )
        );
    } else {
      setSuggestions([]);
    }
  };

  const handleSuggestionClick = (medicine, index) => {
    updateSelection(index, { description: medicine.descripcion, medicine });
    setShowSuggestions(prev => {
      const newShow = [...prev];
      newShow[index] = false;
      return newShow;
    });

    const formats = medicines
      .filter(m => m.descripcion === medicine.descripcion)
      .map(m => m.formato);
    setAvailableFormats(prev => {
      const newFormats = [...prev];
      newFormats[index] = formats;
      return newFormats;
    });

    if (formats.length === 1) {
      updateSelection(index, { format: formats[0] });
    }
  };

  const handleFormatClick = (format, index) => {
    updateSelection(index, { format });
    const selectedMed = medicines.find(
      m =>
        m.descripcion === selections[index].description &&
        m.formato === format
    );
    if (selectedMed) {
      updateSelection(index, { medicine: selectedMed });
    }
    setAvailableFormats(prev => {
      const newFormats = [...prev];
      newFormats[index] = [];
      return newFormats;
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (selections.every(s => s.medicine && s.format)) {
      console.log('Submitted:', selections);
    } else {
      alert('Por favor, seleccione dos medicamentos y sus formatos correspondientes.');
    }
  };

  useEffect(() => {
    const handleClickOutside = (event) => {
      suggestionsRefs.forEach((ref, index) => {
        if (ref.current && !ref.current.contains(event.target)) {
          setShowSuggestions(prev => {
            const newShow = [...prev];
            newShow[index] = false;
            return newShow;
          });
        }
      });
      formatsRefs.forEach((ref, index) => {
        if (ref.current && !ref.current.contains(event.target)) {
          setAvailableFormats(prev => {
            const newFormats = [...prev];
            newFormats[index] = [];
            return newFormats;
          });
        }
      });
    };

    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, []);

  if (!isOpen) return null

  const renderMedicineInput = (index) => (
    <div key={index} className="space-y-4">
      <div className="relative">
        <label htmlFor={`description-${index}`} className="block text-sm font-medium text-gray-700 mb-1">
          Descripción {index + 1}
        </label>
        <input
          type="text"
          id={`description-${index}`}
          value={selections[index].description}
          onChange={(e) => handleDescriptionChange(e, index)}
          className="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500"
          placeholder="Buscar medicamento..."
        />
        {showSuggestions[index] && suggestions.length > 0 && (
          <ul ref={suggestionsRefs[index]} className="absolute z-10 w-full mt-1 bg-white shadow-lg max-h-60 rounded-md py-1 text-base ring-1 ring-black ring-opacity-5 overflow-auto focus:outline-none sm:text-sm">
            {suggestions.map((medicine) => (
              <li
                key={medicine.codigo}
                onClick={() => handleSuggestionClick(medicine, index)}
                className="cursor-pointer select-none relative py-2 pl-3 pr-9 hover:bg-indigo-600 hover:text-white"
              >
                {medicine.descripcion}
              </li>
            ))}
          </ul>
        )}
      </div>
      <div className="relative">
        <label htmlFor={`format-${index}`} className="block text-sm font-medium text-gray-700 mb-1">
          Formato {index + 1}
        </label>
        <input
          type="text"
          id={`format-${index}`}
          value={selections[index].format}
          readOnly
          className="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm bg-gray-100 cursor-pointer"
          placeholder="Seleccione un formato"
          onClick={() => setAvailableFormats(prev => {
            const newFormats = [...prev];
            newFormats[index] = medicines
              .filter(m => m.descripcion === selections[index].description)
              .map(m => m.formato);
            return newFormats;
          })}
        />
        {availableFormats[index].length > 0 && (
          <ul ref={formatsRefs[index]} className="absolute z-10 w-full mt-1 bg-white shadow-lg max-h-60 rounded-md py-1 text-base ring-1 ring-black ring-opacity-5 overflow-auto focus:outline-none sm:text-sm">
            {availableFormats[index].map((formato) => (
              <li
                key={formato}
                onClick={() => handleFormatClick(formato, index)}
                className="cursor-pointer select-none relative py-2 pl-3 pr-9 hover:bg-indigo-600 hover:text-white"
              >
                {formato}
              </li>
            ))}
          </ul>
        )}
      </div>
    </div>
  );

  return (
    <div className="fixed inset-0 bg-gray-600 bg-opacity-50 overflow-y-auto h-full w-full" id="my-modal">
      <div className="relative top-20 mx-auto p-5 border w-96 shadow-lg rounded-md bg-white">
        <div className="mt-3 text-center">
          <h3 className="text-lg leading-6 font-medium text-gray-900">Crear Nueva Receta</h3>
          <div className="mt-2 px-7 py-3">
            <form onSubmit={handleSubmit} className="w-full max-w-2xl space-y-6">
              {renderMedicineInput(0)}
              {renderMedicineInput(1)}
              <button type="button" onClick={onClose} className="px-4 py-2 bg-gray-200 text-gray-800 rounded-md hover:bg-gray-300 focus:outline-none focus:ring-2 focus:ring-gray-400">
                Cancelar
              </button>
              <button type="submit" className="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500">
                Crear Receta
              </button>
            </form>
          </div>
        </div>     
        <button
          onClick={onClose}
          className="absolute top-0 right-0 mt-4 mr-4 text-gray-500 hover:text-gray-800"
        >
        </button>
     
      </div>
    </div>
  )
}

export default CreatePrescriptionModal

