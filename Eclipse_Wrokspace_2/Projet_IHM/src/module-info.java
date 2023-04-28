module ELMEJJADWALID {
	requires javafx.controls;
	
	opens application to javafx.graphics, javafx.fxml;
	opens jeudesFourmis.vue to javafx.graphics, javafx.fxml;
}
