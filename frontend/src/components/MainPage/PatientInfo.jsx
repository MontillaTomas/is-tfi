import React from 'react'

function PatientInfo({ patient }) {

  const calcularEdad = () => {
    const hoy = new Date();
    const nacimiento = new Date(patient.fechaNacimiento);
    let edad = hoy.getFullYear() - nacimiento.getFullYear();
    const mes = hoy.getMonth() - nacimiento.getMonth();
    if (mes < 0 || (mes === 0 && hoy.getDate() < nacimiento.getDate())) {
        edad--;
    }
    return edad;
};

  return (
    <div className="bg-white shadow-md rounded-lg p-6">
      <h2 className="text-2xl font-bold mb-4">Información del Paciente</h2>
      <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-10">
        <div>
          <p className="font-semibold">Nombre:</p>
          <p>{patient.nombre}</p>
        </div>
        <div>
          <p className="font-semibold">Edad:</p>
          <p>{calcularEdad()} años</p>
        </div>
        <div>
          <p className="font-semibold">DNI:</p>
          <p>{patient.dni}</p>
        </div>
        <div>
          <p className="font-semibold">Cuil:</p>
          <p>{patient.cuil}</p>
        </div>
        <div>
          <p className="font-semibold">Obra Social:</p>
          <p>{patient.obraSocial.sigla}</p>
        </div>
        <div>
          <p className="font-semibold">Nro asociado a obra Social:</p>
          <p>{patient.numeroAfiliadoObraSocial}</p>
        </div>
        <div>
          <p className="font-semibold">Email:</p>
          <p>{patient.email}</p>
        </div>
        
      </div>
    </div>
  )
}

export default PatientInfo

