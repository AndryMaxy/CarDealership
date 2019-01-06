package com.andrydevelops.cardealership.database;

class CarDataBase {

    public static final class CarTable{
        public static final String NAME = "car_table";

        public static final class Cols {
            public static final String ID = "car_id";
            public static final String MODEL_ID = "model_id";
            public static final String YEAR_OF_ISSUE = "year_of_issue";
            public static final String BODY_TYPE = "body_type";
            public static final String COLOR = "color";
            public static final String TRANSMISSION = "transmission";
            public static final String PRICE = "price";
            public static final String POWER = "power";
            public static final String IMAGE_NAME = "image_name";
        }
    }

    public static final class ProducerTable{
        public static final String NAME = "producer_table";

        public static final class Cols {
            public static final String PRODUCER_ID = "producer_id";
            public static final String PRODUCER = "producer";
        }
    }

    public static final class ModelTable {
        public static final String NAME = "model_table";

        public static final class Cols {
            public static final String MODEL_ID = "model_id";
            public static final String PRODUCER_ID = "model_producer_id";
            public static final String MODEL = "model";
        }
    }


}
