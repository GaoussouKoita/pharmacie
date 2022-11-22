//Clone prod vente
$("#plus").on("click",function(){

var $tableBody = $('#tbl').find("tbody"),
        $trLast = $tableBody.find("tr:last"),
        $trNew = $trLast.clone();
        $trLast.after($trNew);
});


//Input in Select box
$(function () {
 //Initialize Select2 Elements
    $('.select2bs4').select2({
      theme: 'bootstrap4'
    });
  })


//input file
$(function () {
  bsCustomFileInput.init();
});

//Toast
var Toast = Swal.mixin({
      toast: true,
      position: 'top-end',
      showConfirmButton: false,
      timer: 3000
    });



//new prod approvision
$(document).ready(function () {
               // jQuery button click event to add a row
               var rowIdx =1;

        $('#addBtn').on('click', function () {
            // Adding a row inside the tbody.
            $('#tbody').append(`<tr>
                <td><input class="form-control" name="medicaments[${rowIdx}].medicament.id"
                     placeholder="Sacnner le medicament"   required  type="text"/></td>
                <td><input class="form-control" name="medicaments[${rowIdx}].quantite"
                    type="number" required placeholder="Entrer la quantite"  /></td>
                <td><input class="form-control" name="medicaments[${rowIdx}].prix"
                              type="number" required placeholder="Entrer le prix d'achat par unite de type"  /></td>
                </tr>`);
            rowIdx++;
        });

    });


//new prod Vente
$(document).ready(function () {
               // jQuery button click event to add a row
               var rowIdx =1;

        $('#addBtnVente').on('click', function () {
            // Adding a row inside the tbody.
            $('#tbody').append(`<tr>
            <td><input class="form-control"  name="medicaments[${rowIdx}].medicament.id"
                    placeholder="Sacnner le medicament" required type="number"/></td>
            <td><input class="form-control" name="medicaments[${rowIdx}].quantite"
                    placeholder="Entrer la quantite" required type="number"/></td>
             </tr>`);
            rowIdx++;
        });

    });



//ToolTip
$(document).ready(function(){
  $('[data-toggle="tooltip"]').tooltip();
});


//Dropdown
 $('ul.nav li.nav-item').hover(function () {
         $(this).find('.nav-treeview').stop(true, true).delay(200).fadeIn(500); },
         function () {
            $(this).find('.nav-treeview').stop(true, true).delay(50).fadeOut(500);
    });



// chart

var path= jQuery(location).attr('pathname')
if(path==='/'){
 $.getJSON('http://localhost:8080/statistique/chart-vente-utilisateur', function(data){
    var meds=[];
    $.each(data, function(key, val){
        meds.push(val);
    });
 var my_labels=[];
 var values=[];
 for(var i=0; i<meds.length; i++){
    my_labels.push(meds[i].medicament['nom'])
    values.push(meds[i].quantite)
 }

     'use strict'

      var ticksStyle = {
        fontColor: '#495057',
        fontStyle: 'bold'
      }

      var mode = 'index'
      var intersect = true

      var $venteChart = $('#vente-chart-utilisateur')
      // eslint-disable-next-line no-unused-vars
      var venteChart = new Chart($venteChart, {
        type: 'bar',
        data: {
          labels: my_labels,
          datasets: [
            {
              backgroundColor: '#007bff',
              borderColor: '#007bff',
              data: values
            }
          ]
        },
        options: {
          maintainAspectRatio: false,
          tooltips: {
            mode: mode,
            intersect: intersect
          },
          hover: {
            mode: mode,
            intersect: intersect
          },
          legend: {
            display: false
          },
          scales: {
            yAxes: [{
              // display: false,
              gridLines: {
                display: true,
                lineWidth: '2px',
                color: 'rgba(0, 0, 0, .2)',
                zeroLineColor: 'transparent'
              },
              ticks: $.extend({
                beginAtZero: true,
                callback: function (value) {
                  return value
                }
              }, ticksStyle)
            }],
            xAxes: [{
              display: true,
              gridLines: {
                display: false
              },
              ticks: ticksStyle
            }]
          }
        }
      })


});
}

if(path==='/statistique'){
 $.getJSON('http://localhost:8080/statistique/chart-vente', function(data){
    var meds=[];
    $.each(data, function(key, val){
        meds.push(val);
    });
 var my_labels=[];
 var values=[];
 for(var i=0; i<meds.length; i++){
    my_labels.push(meds[i].medicament['nom'])
    values.push(meds[i].quantite)
 }

     'use strict'

      var ticksStyle = {
        fontColor: '#495057',
        fontStyle: 'bold'
      }

      var mode = 'index'
      var intersect = true

      var $venteChart = $('#chart-vente')
      // eslint-disable-next-line no-unused-vars
      var venteChart = new Chart($venteChart, {
        type: 'bar',
        data: {
          labels: my_labels,
          datasets: [
            {
              backgroundColor: '#007bff',
              borderColor: '#007bff',
              data: values
            }
          ]
        },
        options: {
          maintainAspectRatio: false,
          tooltips: {
            mode: mode,
            intersect: intersect
          },
          hover: {
            mode: mode,
            intersect: intersect
          },
          legend: {
            display: false
          },
          scales: {
            yAxes: [{
              // display: false,
              gridLines: {
                display: true,
                lineWidth: '2px',
                color: 'rgba(0, 0, 0, .2)',
                zeroLineColor: 'transparent'
              },
              ticks: $.extend({
                beginAtZero: true,
                callback: function (value) {
                  return value
                }
              }, ticksStyle)
            }],
            xAxes: [{
              display: true,
              gridLines: {
                display: false
              },
              ticks: ticksStyle
            }]
          }
        }
      })


});
}

if(path==='/statistique/details'){
    var params=new URLSearchParams(window.location.search)
    params.has('dateDebut')
    var dateDebut=params.get('dateDebut')
    params.has('date2')
    var dateFin=params.get('dateFin')

    $.getJSON('http://localhost:8080/statistique/chart-ventes?dateDebut='+dateDebut+'&dateFin='+dateFin, function(data){
   var meds=[];
       $.each(data, function(key, val){
           meds.push(val);
       });
    var my_labels=[];
    var values=[];
    for(var i=0; i<meds.length; i++){
       my_labels.push(meds[i].medicament['nom'])
       values.push(meds[i].quantite)
    }

     'use strict'

      var ticksStyle = {
        fontColor: '#495057',
        fontStyle: 'bold'
      }

      var mode = 'index'
      var intersect = true

      var $venteChart = $('#chart-vente')
      // eslint-disable-next-line no-unused-vars
      var venteChart = new Chart($venteChart, {
        type: 'bar',
        data: {
          labels: my_labels,
          datasets: [
            {
              backgroundColor: '#007bff',
              borderColor: '#007bff',
              data: values
            }
          ]
        },
        options: {
          maintainAspectRatio: false,
          tooltips: {
            mode: mode,
            intersect: intersect
          },
          hover: {
            mode: mode,
            intersect: intersect
          },
          legend: {
            display: false
          },
          scales: {
            yAxes: [{
              // display: false,
              gridLines: {
                display: true,
                lineWidth: '2px',
                color: 'rgba(0, 0, 0, .2)',
                zeroLineColor: 'transparent'
              },
              ticks: $.extend({
                beginAtZero: true,
                callback: function (value) {
                  return value
                }
              }, ticksStyle)
            }],
            xAxes: [{
              display: true,
              gridLines: {
                display: false
              },
              ticks: ticksStyle
            }]
          }
        }
      })
});
}


//PIE CHART
var pieChartCanvas = $('#pieChart').get(0).getContext('2d')
$.getJSON('http://localhost:8080/statistique/chart-vente-utilisateurs', function (data) {
  var ventes = [];
  $.each(data, function (key, val) {
    ventes.push(val);
  });
  var my_labels = [];
  var values = [];
  for (var i = 0; i < ventes.length; i++) {
    my_labels.push(ventes[i].utilisateur['prenom']+' '+ventes[i].utilisateur['nom'])
    values.push(ventes[i].montantAPayer)
  }

  var pieData = {
    labels: my_labels,
    datasets: [
      {
        data: values,
        backgroundColor: ['#f56954', '#00a65a', '#f39c12', '#00c0ef', '#3c8dbc', '#d2d6de'],
      }
    ]
  }
  var pieOptions = {
    maintainAspectRatio: false,
    responsive: true,
  }
  //Create pie or douhnut chart
  // You can switch between pie and douhnut using the method below.
  new Chart(pieChartCanvas, {
    type: 'pie',
    data: pieData,
    options: pieOptions
  })
})
