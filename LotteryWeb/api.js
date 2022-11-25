const uri_region = "http://localhost:8080/api/lottery/latest?region=";
function getLotterySouth() {
    fetch(uri_region+"Miền Nam")
      .then(response => response.json())
      .then(data => render(data,"#south"))
      .catch(error => console.error('Unable to get items.', error));
  }
  function getLotteryCenter() {
    fetch(uri_region+"Miền Trung")
      .then(response => response.json())
      .then(data => render(data,"#center"))
      .catch(error => console.error('Unable to get items.', error));
  }
  function getLotteryNorth() {
    fetch(uri_region+"Miền Bắc")
      .then(response => response.json())
      .then(data => render2(data,"#north",0))
      .catch(error => console.error('Unable to get items.', error));
  }
function render(data,id){
    const title = document.querySelector(id+" .text-center.mb-2");
    title.innerHTML = `<a href="#" style ="font-size: 25px"> Xổ số ${data[0].region} ${new Date(data[0].issueDate).
                                                                                toLocaleDateString("vn-VN")} </a>`;
    const tRow = document.querySelector(id+' .table-bordered tr');
    let header = data.map((element, index) =>{
        return `<th scope="col">
        <a href="" class="text-white" title="Xổ số ${data[index].province}"
          >${data[index].province}</a
        >
      </th>`;
    })
    tRow.innerHTML ='<th scope="col"></th>'+ header.join('');
    //
    const tBody = document.querySelectorAll(id+' .table-bordered tbody tr');
    let eightPrize = data.map((element, index) =>{
        return `  <td><div>${data[index].resultEighthPrizes.replaceAll('-','<br>')}</div></td>`;
    })
    tBody[0].innerHTML ='<th scope="row">G8</th>'+ eightPrize.join('');
    //
    let seventhPrize = data.map((element, index) =>{
        return `  <td><div>${data[index].resultSeventhPrizes.replaceAll('-','<br>')}</div></td>`;
    })
    tBody[1].innerHTML ='<th scope="row">G7</th>'+ seventhPrize.join('');
    //
    let sixPrize = data.map((element, index) =>{
        return `  <td><div>${data[index].resultSixthPrizes.replaceAll('-','<br>')}</div></td>`;
    })
    tBody[2].innerHTML ='<th scope="row">G6</th>'+ sixPrize.join('');
    
    let fifthPrize = data.map((element, index) =>{
        return `  <td><div>${data[index].resultFifthPrizes.replaceAll('-','<br>')}</div></td>`;
    })
    tBody[3].innerHTML ='<th scope="row">G5</th>'+ fifthPrize.join('');

    let fourthPrize = data.map((element, index) =>{
        return `  <td><div>${data[index].resultFourthPrizes.replaceAll('-','<br>')}</div></td>`;
    })
    tBody[4].innerHTML ='<th scope="row">G4</th>'+ fourthPrize.join('');

    let thirdPrize = data.map((element, index) =>{
        return `  <td><div>${data[index].resultThirdPrizes.replaceAll('-','<br>')}</div></td>`;
    })
    tBody[5].innerHTML ='<th scope="row">G3</th>'+ thirdPrize.join('');

    let secondPrize = data.map((element, index) =>{
        return `  <td><div>${data[index].resultSecondPrizes.replaceAll('-','<br>')}</div></td>`;
    })
    tBody[6].innerHTML ='<th scope="row">G2</th>'+ secondPrize.join('');

    let firstPrize = data.map((element, index) =>{
        return `  <td><div>${data[index].resultFirstPrizes.replaceAll('-','<br>')}</div></td>`;
    })
    tBody[7].innerHTML ='<th scope="row">G1</th>'+ firstPrize.join('');

    let specialPrize = data.map((element, index) =>{
        return `  <td><div>${data[index].resultSpecialPizes.replaceAll('-','<br>')}</div></td>`;
    })
    tBody[8].innerHTML ='<th scope="row">GĐB</th>'+ specialPrize.join('');

    

}
function render2(data,id,index){
    const title = document.querySelector(id+" .text-center.mb-2");
    title.innerHTML = `<a href="#" style ="font-size: 25px"> Xổ số ${data[index].region} ${new Date(data[index].issueDate).
                                                                                toLocaleDateString("vn-VN")} </a>`;
    const tRow = document.querySelector(id+' .table-bordered tr');
    let header =   `<th scope="col">
        <a href="" class="text-white" title="Xổ số ${data[index].province}"
          >${data[index].province}</a
        >
      </th>`;

    tRow.innerHTML ='<th scope="col"></th>'+ header
    //
    const tBody = document.querySelectorAll(id+' .table-bordered tbody tr');
    let specialPrize = 
       `  <td><div>${data[index].resultSpecialPizes}</div></td>`;

    tBody[0].innerHTML ='<th scope="row">GĐB</th>'+ specialPrize
    //
    let firstPrize =
       `  <td><div>${data[index].resultFirstPrizes}</div></td>`;

    tBody[1].innerHTML ='<th scope="row">G1</th>'+ firstPrize
    //
    let secondPrize = 
       `  <td><div>${data[index].resultSecondPrizes}</div></td>`;
 
    tBody[2].innerHTML ='<th scope="row">G2</th>'+ secondPrize
    
    let thirdPrize = 
        `  <td><div>${data[index].resultThirdPrizes}</div></td>`;

    tBody[3].innerHTML ='<th scope="row">G3</th>'+ thirdPrize

    let fourthPrize = 
        `  <td><div>${data[index].resultFourthPrizes}</div></td>`;

    tBody[4].innerHTML ='<th scope="row">G4</th>'+ fourthPrize

    let fifthPrize = 
        `  <td><div>${data[index].resultFifthPrizes}</div></td>`;

    tBody[5].innerHTML ='<th scope="row">G5</th>'+ fifthPrize

    let sixthPrize = 
       `  <td><div>${data[index].resultSixthPrizes}</div></td>`;

    tBody[6].innerHTML ='<th scope="row">G6</th>'+ sixthPrize

    let sevenPrize =
       `  <td><div>${data[index].resultSeventhPrizes}</div></td>`;

    tBody[7].innerHTML ='<th scope="row">G7</th>'+ sevenPrize



    

}