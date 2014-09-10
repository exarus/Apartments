<%@ page import="ua.kiev.prog.ApartmentDatabase" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.Set" %>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 11.07.2014
  Time: 16:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!
    ApartmentDatabase db = ApartmentDatabase.getInstance();
    float maxArea = 0;
    float minArea = 0;
    double  maxPrice = 0;
    double minPrice = 0;
    byte averageRoomCount = 0;
    Set<String> districtSet = null;
%>

<%
    try {
        districtSet = db.getDistrictSet();
        maxPrice    = db.getMaxPrice();
        minPrice    = db.getMinPrice();
        maxArea     = db.getMaxArea();
        minArea     = db.getMinArea();
        averageRoomCount = (byte) ((db.getMinRoomCount() + db.getMaxRoomCount()) / 2);
    } catch (SQLException e) {
        response.sendError(503, ": Database server not responding.");
    }
%>

<html>
  <head>
    <title>Настройки поиска</title>
    <link rel="stylesheet" type="text/css" href="img/style.css" />
    <script type="text/javascript" src="DoubleTrackBar.js"></script>
  </head>

  <script type="text/javascript">
      function $(id) {
          return document.getElementById(id);
      }
      function classFilter(r,m,not){
          m = " " + m + " ";
          var tmp = [];
          for ( var i = 0; r[i]; i++ ) {
              var pass = (" " + r[i].className + " ").indexOf( m ) >= 0;
              if ( not ^ pass )
                  tmp.push( r[i] );
          }
          return tmp;
      }
  </script>

  <body>
  <div id="container">
  <form action="${pageContext.request.contextPath}/select" method="POST">
      <label>
          <h3>Цена:</h3>
          От:
          <input type="text" pattern="\d+([.,]\d*)?" required value="<%=minPrice%>"
                 name="minPrice" id="DoubleTrack-5-InputMin">
          До:
          <input type="text" pattern="\d+([.,]\d*)?" value="<%=maxPrice%>"
                 name="maxPrice" id="DoubleTrack-5-InputMax"><br>
          <div id="DoubleTrack-5" class="DoubleTrackBar">
              <img src="img/line-l.gif" alt="" class="start" />
              <img src="img/line-r.gif" alt="" class="finish" />
              <div id="DoubleTrack-5-Tracker" class="Tracker">
                  <img src="img/track-l.gif" alt='' class="fll" />
                  <img src="img/track-r.gif" alt='' class="flr" />
                  <span class="fll text" id="DoubleTrack-5-LeftText"></span>
                  <span class="flr text" id="DoubleTrack-5-RightText"></span>
              </div>
          </div>
          <script type="text/javascript" defer>
              var DoubleTrackBar = new cDoubleTrackBar('DoubleTrack-5', 'DoubleTrack-5-Tracker', {
                  OnUpdate: function(){
                      $('DoubleTrack-5-LeftText').innerHTML = this.leadSpaces(this.MinPos);
                      $('DoubleTrack-5-RightText').innerHTML = this.leadSpaces(this.MaxPos);
                      $('DoubleTrack-5-InputMin').value = this.MinPos;
                      $('DoubleTrack-5-InputMax').value = this.MaxPos;

                      this.Tracker.style.backgroundPosition = -this.TrackerLeft + 'px center';
                      if (this.fix)
                          for (var i in this.fix)
                              this.fix[i].style.left = (this.TrackerRight - this.TrackerLeft) + 'px';
                  },
                  Min: <%=(int)minPrice - 1%>,
                  Max: <%=maxPrice + 1%>,
                  FingerOffset: 8,
                  MinSpace: 0,
                  RoundTo: 0,
                  Margins: 30,
                  FormatNumbers: true,
                  AllowedValues: true
              });
              DoubleTrackBar.AutoHairline(6);
              DoubleTrackBar.fix = classFilter(DoubleTrackBar.Tracker.getElementsByTagName('*'), 'flr');
              DoubleTrackBar.Track.style.visibility = 'visible';
          </script>
      </label><br>
      <label>
          <b>Площадь: </b><br>
          От:
          <input type="text" pattern="\d+([.,]\d*)?" required value="<%=minArea%>" name="minArea"><br>
          До:
          <input type="text" pattern="\d+([.,]\d*)?" required value="<%=maxArea%>" name="maxArea"><br>
      </label><br>
      <label>
          <b>Количество комнат: </b>
          <input type="number" pattern="\d+" required value="<%=averageRoomCount%>" name="roomCount"><br>
      </label><br>
      <label>
          <b>Район: </b>
          <input type="text" pattern="\D+" required value="<%=districtSet.iterator().next()%>" name="district"><br>
      </label><br>
      <button>Поиск</button>
  </form>
  </div>
  </body>
</html>
