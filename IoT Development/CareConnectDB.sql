DROP DATABASE IF EXISTS patient_management;


CREATE DATABASE IF NOT EXISTS patient_management;
USE patient_management;

DROP TABLE IF EXISTS Patients, Doctors, Caregivers;



CREATE TABLE Doctors (
    DoctorID INT PRIMARY KEY,
    FirstName VARCHAR(50) NOT NULL,
    LastName VARCHAR(50) NOT NULL,
    Specialty VARCHAR(50),
	  PhoneNumber VARCHAR(15)
);

CREATE TABLE Caregivers (
    CaregiverID INT PRIMARY KEY,
    FirstName VARCHAR(50) NOT NULL,
    LastName VARCHAR(50) NOT NULL,
    PhoneNumber VARCHAR(15)
);

CREATE TABLE Patients (
    PatientID INT PRIMARY KEY,
	  DoctorID INT,
    CaregiverID INT,
    FirstName VARCHAR(50) NOT NULL,
    LastName VARCHAR(50) NOT NULL,
    Address VARCHAR(255),
    PhoneNumber VARCHAR(15),
    ClinicalHistory TEXT,
    FOREIGN KEY (DoctorID) REFERENCES Doctors(DoctorID),
    FOREIGN KEY (CaregiverID) REFERENCES Caregivers(CaregiverID)
);


INSERT INTO Doctors VALUES(1, "Emily", "Johnson", "Neurology", "897867544");
INSERT INTO Doctors VALUES(2, "Liam", "Byrne", "Geriatrics", "897867544");
INSERT INTO Doctors VALUES(3, "Siobhan", "Walsh", "Psychiatry", "897867544");
INSERT INTO Doctors VALUES(4, "Aoife", "Brennan", "Internal Medicine", "897867544");
INSERT INTO Doctors VALUES(5, "Niamh", "Murphy", "Neurology", "897867544");
INSERT INTO Doctors VALUES(6, "Michael", "Byrne", "Geriatrics", "897867544");


INSERT INTO Caregivers VALUES(1, "Emma", "Mc Donald","897863334");
INSERT INTO Caregivers VALUES(2, "Mark", "O'Connor", "789867545");
INSERT INTO Caregivers VALUES(3, "Fiona", "Walsh","897866667");
INSERT INTO Caregivers VALUES(4, "Sean", "O'Donnell","897860980");
INSERT INTO Caregivers VALUES(5, "David", "Walsh","896863685");
INSERT INTO Caregivers VALUES(6, "Fiona", "Murphy","878965349");


INSERT INTO Patients VALUES
  (1, 1, 2, "Sean", "Murphy", "456 Oak Lane, Dublin", "12345678",
    "Date of Diagnosis: October 5, 2022
    Primary Care Physician: Dr. Emily Johnson
    Referring Physician (if applicable): Dr. Sarah Anderson
    Presenting Complaint: Family reports memory problems, confusion, and disorientation.
    Cognitive Function Assessment: Mini-Mental State Examination score of 18/30
    Behavioral Observations: Patient frequently repeats questions, appears disoriented to time and place, and exhibits occasional agitation.
    Functional Assessment: Struggles with daily tasks such as dressing, cooking, and managing finances.
    Medical History: Hypertension, Type 2 diabetes, and a family history of Alzheimer's disease.
    Imaging Results (if applicable): MRI shows generalized brain atrophy.
    Diagnosis: Alzheimer's Disease
    Differential Diagnoses Considered: Vascular Dementia, Mild Cognitive Impairment
    Treatment Plan: Medications: Prescribed Donepezil to manage cognitive symptoms. Behavioral Interventions: Recommend establishing a daily routine, regular exercise, and caregiver support. Follow-up Plan: Schedule a follow-up appointment in 3 months to monitor progress and adjust treatment as needed."
    );

INSERT INTO Patients VALUES
  (2,2,3, "Aisling", "O'Connor", "789 Maple Street, Cork", "218765432",
    "Date of Diagnosis: September 12, 2022
    Primary Care Physician: Dr. Liam Byrne
    Referring Physician (if applicable): Dr. Emma Murphy
    Presenting Complaint: Family reports memory problems, confusion, and disorientation.
    Cognitive Function Assessment: Mini-Mental State Examination (MMSE) score of 20/30
    Behavioral Observations: Patient occasionally forgets names, exhibits restlessness, and has difficulty concentrating.
    Functional Assessment: Experiences challenges with organizing daily tasks and frequently misplaces personal items.
    Medical History: Hypertension and a family history of dementia.
    Imaging Results (if applicable): CT scan shows mild cortical atrophy.
    Diagnosis: Mild Cognitive Impairment
    Differential Diagnoses Considered: Alzheimer's Disease, Age-related cognitive decline
    Treatment Plan: Recommend cognitive training exercises and regular cognitive assessments. Schedule a follow-up appointment in 6 months to assess progress."
    );

INSERT INTO Patients VALUES
  (3,3,1, "Liam", "Kelly", "101 Pine Road, Galway", "913456789",
    "Date of Diagnosis: November 8, 2022
    Primary Care Physician: Dr. Siobhan Walsh
    Referring Physician (if applicable): Dr. Conor Murphy
    Presenting Complaint: Family reports memory problems, confusion, and disorientation.
    Cognitive Function Assessment: Mini-Mental State Examination score of 16/30
    Behavioral Observations: Patient experiences significant memory loss, exhibits wandering behavior, and demonstrates difficulty recognizing familiar faces.
    Functional Assessment: Requires assistance with all daily activities, including personal care and feeding.
    Medical History: Hypertension, Type 2 diabetes, and a family history of Alzheimer's disease.
    Imaging Results (if applicable): PET scan reveals significant hypometabolism in the temporal and parietal lobes.
    Diagnosis: Advanced Alzheimer's Disease
    Differential Diagnoses Considered: Lewy Body Dementia, Frontotemporal Dementia
    Treatment Plan: Focus on supportive care and managing symptoms. Schedule regular check-ins with the caregiver to assess the patient's well-being and provide support."
    );

INSERT INTO Patients VALUES
  (4,5,5, "Eileen", "Doherty", "222 Elm Street, Limerick", "619876543",
    "Date of Diagnosis: December 15, 2022
    Primary Care Physician: Dr. Aoife Brennan
    Referring Physician (if applicable): Dr. Sean O'Donnell
    Presenting Complaint: Family reports memory problems, confusion, and disorientation.
    Cognitive Function Assessment: Mini-Mental State Examination score of 22/30
    Behavioral Observations: Patient occasionally forgets recent events, exhibits repetitive behaviors, and has difficulty with decision-making.
    Functional Assessment: Maintains independence in basic daily activities but needs reminders for complex tasks.
    Medical History: Hypertension and a family history of Alzheimer's disease.
    Imaging Results (if applicable): Normal brain imaging.
    Diagnosis: Early Alzheimer's Disease
    Differential Diagnoses Considered: Mild Cognitive Impairment, Age-related cognitive decline
    Treatment Plan: Monitor cognitive function, provide education to the family on managing symptoms, and schedule regular check-ups."
    );

INSERT INTO Patients VALUES
  (5,6,4, "Ciaran", "O'Sullivan", "555 Birch Avenue, Waterford", "518765432",
    "Date of Diagnosis: January 20, 2023
    Primary Care Physician: Dr. Niamh Murphy
    Referring Physician (if applicable): Dr. David Walsh
    Presenting Complaint: Family reports memory problems, difficulty finding words, and challenges in social situations.
    Cognitive Function Assessment: Mini-Mental State Examination score of 25/30
    Behavioral Observations: Patient exhibits occasional memory lapses, has difficulty with word recall, and shows mild anxiety.
    Functional Assessment: Maintains independence in daily activities but needs assistance with complex tasks.
    Medical History: Hypertension and a family history of dementia.
    Imaging Results (if applicable): Normal brain imaging.
    Diagnosis: Mild Cognitive Impairment
    Differential Diagnoses Considered: Alzheimer's Disease, Age-related cognitive decline
    Treatment Plan: Implement cognitive strategies, encourage social engagement, and schedule regular follow-ups to monitor progression."
    );

INSERT INTO Patients VALUES
  (6, 4, 6, "Orla", "Gallagher", "14 Oak Drive, Drogheda", "812345678",
    "Date of Diagnosis: February 5, 2023
    Primary Care Physician: Dr. Michael Byrne
    Referring Physician (if applicable): Dr. Fiona Murphy
    Presenting Complaint: Family reports memory problems, disorientation, and difficulty with decision-making.
    Cognitive Function Assessment: Mini-Mental State Examination score of 19/30
    Behavioral Observations: Patient frequently forgets recent events, has difficulty concentrating, and exhibits occasional restlessness.
    Functional Assessment: Requires assistance with most daily activities, including personal care and meal preparation.
    Medical History: Hypertension, Type 2 diabetes, and a family history of Alzheimer's disease.
    Imaging Results (if applicable): CT scan shows moderate cortical atrophy.
    Diagnosis: Moderate Alzheimer's Disease
    Differential Diagnoses Considered: Vascular Dementia, Frontotemporal Dementia
    Treatment Plan: Focus on symptom management, implement safety measures, and provide caregiver support. Schedule regular check-ups to assess overall well-being."
   );
	

