

export default function ReportGroupStudents(props) {
    return (
        <div className="container-fluid justify-content-center align-items-center d-flex flex-column my-2">
            <div>
                <h6>Расписание<img className="pdf-size " src="img/Rasp.png" alt="Rasp"/></h6>
            </div>
            <div> <a href="Rasp(1-4).pdf" target="_blank"><img className="pdf-size" src="img/6666.png"/></a><a href="Rasp(1-4).pdf" target="_blank">Расписание 1 - 4 кл. (.pdf)</a></div><br/><br/>
            <div> <a href="Rasp(5-11).pdf" target="_blank"><img className="pdf-size" src="img/6666.png"/></a><a href="Rasp(5-11).pdf" target="_blank">Расписание 5 - 11 кл. (.pdf)</a></div>
        </div>
    );
}