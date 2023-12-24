
  $(function() {
    /* ChartJS
     * -------
     * Data and config for chartjs
     */
    'use strict';
    var clickcart_x = clickCarts.map(x => x[[0][0]])
    var clickcart_y = clickCarts.map(x => x[[1][0]])
    console.log(clickCarts);
    console.log(clickcart_x);
    console.log(clickcart_y);
    var datacart = {
      labels: clickcart_x,
      datasets: [{
        label: '# of view',
        data: clickcart_y,
        backgroundColor: [
          'rgba(255, 99, 132, 0.2)',
          'rgba(54, 162, 235, 0.2)',
          'rgba(255, 206, 86, 0.2)',
          'rgba(75, 192, 192, 0.2)',
          'rgba(153, 102, 255, 0.2)',
          'rgba(200, 200, 10, 0.2)',
          'rgba(30, 159, 64, 0.2)',
          'rgba(100, 10, 64, 0.2)',
          'rgba(253, 10, 64, 0.2)',
          'rgba(299, 100, 64, 0.2)'
        ],
        borderColor: [
          'rgba(255,99,132,1)',
          'rgba(54, 162, 235, 1)',
          'rgba(255, 206, 86, 1)',
          'rgba(75, 192, 192, 1)',
          'rgba(153, 102, 255, 1)',
          'rgba(255, 159, 64, 1)'
        ],
        borderWidth: 1,
        fill: false
      }]
    };
    var clickview_x = clickViewProduct.map(x => x[[0][0]])
    var clickview_y = clickViewProduct.map(x => x[[1][0]])
    console.log(clickViewProduct);
    console.log(clickview_x);
    console.log(clickview_y);
    var dataview = {
      labels: clickview_x,
      datasets: [{
        label: '# of view',
        data: clickview_y,
        backgroundColor: [
          'rgba(255, 99, 132, 0.2)',
          'rgba(54, 162, 235, 0.2)',
          'rgba(255, 206, 86, 0.2)',
          'rgba(75, 192, 192, 0.2)',
          'rgba(153, 102, 255, 0.2)',
          'rgba(200, 200, 10, 0.2)',
          'rgba(30, 159, 64, 0.2)',
          'rgba(100, 10, 64, 0.2)',
          'rgba(253, 10, 64, 0.2)',
          'rgba(299, 100, 64, 0.2)'
        ],
        borderColor: [
          'rgba(255,99,132,1)',
          'rgba(54, 162, 235, 1)',
          'rgba(255, 206, 86, 1)',
          'rgba(75, 192, 192, 1)',
          'rgba(153, 102, 255, 1)',
          'rgba(255, 159, 64, 1)'
        ],
        borderWidth: 1,
        fill: false
      }]
    };


    var orderinmonth_x = orderInMonth.map(x => x[[0][0]])
    var orderinmonth_y1 = orderInMonth.map(x => x[[1][0]])
    var orderCounty = orderCountInMonth.map(x => x[[1][0]])
    console.log(orderInMonth)
    var multiLineData = {
      labels: orderinmonth_x,
      datasets: [{
        label: 'Đơn hàng',
        data: orderinmonth_y1,
        borderColor: [
          '#587ce4'
        ],
        borderWidth: 2,
        fill: false
      },
        {
          label: 'Sản phẩm đã bán',
          data: orderCounty,
          borderColor: [
            '#ede190'
          ],
          borderWidth: 2,
          fill: false
        }
      ]
    };
    var options = {
      scales: {
        yAxes: [{
          ticks: {
            beginAtZero: true
          }
        }]
      },
      legend: {
        display: false
      },
      elements: {
        point: {
          radius: 0
        }
      }

    };
    var clickCartPieMorning = clickCartPie[0]
    var clickCartPieNoon = clickCartPie[1]
    var clickCartPieAfterNoon = clickCartPie[2]
    var clickCartPieEvening = clickCartPie[3]
    var clickCartPieNight = clickCartPie[4]
    console.log(clickCartPie);
    console.log(clickCartPieMorning);
    console.log(clickCartPieNoon);
    var doughnutPieData = {
      datasets: [{
        data: [clickCartPieMorning, clickCartPieNoon, clickCartPieAfterNoon, clickCartPieEvening, clickCartPieNight],
        backgroundColor: [
          'rgba(255, 99, 132, 0.5)',
          'rgba(54, 162, 235, 0.5)',
          'rgba(255, 206, 86, 0.5)',
          'rgba(75, 192, 192, 0.5)',
          'rgba(153, 102, 255, 0.5)',
          'rgba(255, 159, 64, 0.5)'
        ],
        borderColor: [
          'rgba(255,99,132,1)',
          'rgba(54, 162, 235, 1)',
          'rgba(255, 206, 86, 1)',
          'rgba(75, 192, 192, 1)',
          'rgba(153, 102, 255, 1)',
          'rgba(255, 159, 64, 1)'
        ],
      }],

      // These labels appear in the legend and in the tooltips when hovering different arcs
      labels: [
        'Sáng',
        'Trưa',
        'Chiều',
        'Tối',
        'Đêm'
      ]
    };

    var clickViewPieMorning = clickViewPie[0]
    var clickViewPieNoon = clickViewPie[1]
    var clickViewPieAfterNoon = clickViewPie[2]
    var clickViewPieEvening = clickViewPie[3]
    var clickViewPieNight = clickViewPie[4]
    var pieData = {
      datasets: [{
        data: [clickViewPieMorning, clickViewPieNoon, clickViewPieAfterNoon, clickViewPieEvening, clickViewPieNight],
        backgroundColor: [
          'rgba(255, 99, 132, 0.5)',
          'rgba(54, 162, 235, 0.5)',
          'rgba(255, 206, 86, 0.5)',
          'rgba(75, 192, 192, 0.5)',
          'rgba(153, 102, 255, 0.5)',
          'rgba(255, 159, 64, 0.5)'
        ],
        borderColor: [
          'rgba(255,99,132,1)',
          'rgba(54, 162, 235, 1)',
          'rgba(255, 206, 86, 1)',
          'rgba(75, 192, 192, 1)',
          'rgba(153, 102, 255, 1)',
          'rgba(255, 159, 64, 1)'
        ],
      }],

      // These labels appear in the legend and in the tooltips when hovering different arcs
      labels: [
        'Sáng',
        'Trưa',
        'Chiều',
        'Tối',
        'Đêm'
      ]
    };
    var doughnutPieOptions = {
      responsive: true,
      animation: {
        animateScale: true,
        animateRotate: true
      }
    };
    var areaData = {
      labels: ["2013", "2014", "2015", "2016", "2017"],
      datasets: [{
        label: '# of Votes',
        data: [12, 19, 3, 5, 2, 3],
        backgroundColor: [
          'rgba(255, 99, 132, 0.2)',
          'rgba(54, 162, 235, 0.2)',
          'rgba(255, 206, 86, 0.2)',
          'rgba(75, 192, 192, 0.2)',
          'rgba(153, 102, 255, 0.2)',
          'rgba(255, 159, 64, 0.2)'
        ],
        borderColor: [
          'rgba(255,99,132,1)',
          'rgba(54, 162, 235, 1)',
          'rgba(255, 206, 86, 1)',
          'rgba(75, 192, 192, 1)',
          'rgba(153, 102, 255, 1)',
          'rgba(255, 159, 64, 1)'
        ],
        borderWidth: 1,
        fill: true, // 3: no fill
      }]
    };

    var areaOptions = {
      plugins: {
        filler: {
          propagate: true
        }
      }
    }

    var multiAreaData = {
      labels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
      datasets: [{
        label: 'Facebook',
        data: [8, 11, 13, 15, 12, 13, 16, 15, 13, 19, 11, 14],
        borderColor: ['rgba(255, 99, 132, 0.5)'],
        backgroundColor: ['rgba(255, 99, 132, 0.5)'],
        borderWidth: 1,
        fill: true
      },
        {
          label: 'Twitter',
          data: [7, 17, 12, 16, 14, 18, 16, 12, 15, 11, 13, 9],
          borderColor: ['rgba(54, 162, 235, 0.5)'],
          backgroundColor: ['rgba(54, 162, 235, 0.5)'],
          borderWidth: 1,
          fill: true
        },
        {
          label: 'Linkedin',
          data: [6, 14, 16, 20, 12, 18, 15, 12, 17, 19, 15, 11],
          borderColor: ['rgba(255, 206, 86, 0.5)'],
          backgroundColor: ['rgba(255, 206, 86, 0.5)'],
          borderWidth: 1,
          fill: true
        }
      ]
    };

    var multiAreaOptions = {
      plugins: {
        filler: {
          propagate: true
        }
      },
      elements: {
        point: {
          radius: 0
        }
      },
      scales: {
        xAxes: [{
          gridLines: {
            display: false
          }
        }],
        yAxes: [{
          gridLines: {
            display: false
          }
        }]
      }
    }

    var scatterChartData = {
      datasets: [{
        label: 'First Dataset',
        data: [{
          x: -10,
          y: 0
        },
          {
            x: 0,
            y: 3
          },
          {
            x: -25,
            y: 5
          },
          {
            x: 40,
            y: 5
          }
        ],
        backgroundColor: [
          'rgba(255, 99, 132, 0.2)'
        ],
        borderColor: [
          'rgba(255,99,132,1)'
        ],
        borderWidth: 1
      },
        {
          label: 'Second Dataset',
          data: [{
            x: 10,
            y: 5
          },
            {
              x: 20,
              y: -30
            },
            {
              x: -25,
              y: 15
            },
            {
              x: -10,
              y: 5
            }
          ],
          backgroundColor: [
            'rgba(54, 162, 235, 0.2)',
          ],
          borderColor: [
            'rgba(54, 162, 235, 1)',
          ],
          borderWidth: 1
        }
      ]
    }

    var scatterChartOptions = {
      scales: {
        xAxes: [{
          type: 'linear',
          position: 'bottom'
        }]
      }
    }
    // Get context with jQuery - using jQuery's .get() method.
    if ($("#barChart1").length) {
      var barChartCanvas = $("#barChart1").get(0).getContext("2d");
      // This will get the first returned node in the jQuery collection.
      var barChart1 = new Chart(barChartCanvas, {
        type: 'bar',
        data: datacart,
        options: options
      });
    }
    if ($("#barChart2").length) {
      var barChartCanvas = $("#barChart2").get(0).getContext("2d");
      // This will get the first returned node in the jQuery collection.
      var barChart2 = new Chart(barChartCanvas, {
        type: 'bar',
        data: dataview,
        options: options
      });
    }
    if ($("#lineChart").length) {
      var lineChartCanvas = $("#lineChart").get(0).getContext("2d");
      var lineChart = new Chart(lineChartCanvas, {
        type: 'line',
        data: data,
        options: options
      });
    }

    if ($("#linechart-multi").length) {
      var multiLineCanvas = $("#linechart-multi").get(0).getContext("2d");
      var lineChart = new Chart(multiLineCanvas, {
        type: 'line',
        data: multiLineData,
        options: options
      });
    }

    if ($("#areachart-multi").length) {
      var multiAreaCanvas = $("#areachart-multi").get(0).getContext("2d");
      var multiAreaChart = new Chart(multiAreaCanvas, {
        type: 'line',
        data: multiAreaData,
        options: multiAreaOptions
      });
    }

    if ($("#doughnutChart").length) {
      var doughnutChartCanvas = $("#doughnutChart").get(0).getContext("2d");
      var doughnutChart = new Chart(doughnutChartCanvas, {
        type: 'doughnut',
        data: doughnutPieData,
        options: doughnutPieOptions
      });
    }

    if ($("#pieChart").length) {
      var pieChartCanvas = $("#pieChart").get(0).getContext("2d");
      var pieChart = new Chart(pieChartCanvas, {
        type: 'pie',
        data: pieData,
        options: doughnutPieOptions
      });
    }

    if ($("#areaChart").length) {
      var areaChartCanvas = $("#areaChart").get(0).getContext("2d");
      var areaChart = new Chart(areaChartCanvas, {
        type: 'line',
        data: areaData,
        options: areaOptions
      });
    }

    if ($("#scatterChart").length) {
      var scatterChartCanvas = $("#scatterChart").get(0).getContext("2d");
      var scatterChart = new Chart(scatterChartCanvas, {
        type: 'scatter',
        data: scatterChartData,
        options: scatterChartOptions
      });
    }

    if ($("#browserTrafficChart").length) {
      var doughnutChartCanvas = $("#browserTrafficChart").get(0).getContext("2d");
      var doughnutChart = new Chart(doughnutChartCanvas, {
        type: 'doughnut',
        data: browserTrafficData,
        options: doughnutPieOptions
      });
    }
  });
