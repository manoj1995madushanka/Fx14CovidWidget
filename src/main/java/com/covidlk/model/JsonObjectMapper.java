package com.covidlk.model;

import com.google.gson.JsonObject;

@lombok.Data
public class JsonObjectMapper {

    /*update_date_time": "2020-07-17 23:01:30",
        "local_new_cases": 10,
        "local_total_cases": 2697,
        "local_total_number_of_individuals_in_hospitals": 99,
        "local_deaths": 11,
        "local_new_deaths": 0,
        "local_recovered": 2012,
        "local_active_cases": 674,
        "global_new_cases": 234417,
        "global_total_cases": 13706050,
        "global_deaths": 587144,
        "global_new_deaths": 5760,
        "global_recovered": 8166465,
        "total_pcr_testing_count": 132796,*/
    private int local_new_cases;
    private int local_total_cases;
    private int local_total_number_of_individuals_in_hospitals;
    private int local_total_deaths;
    private int local_today_deaths;
    private int local_recovered;
    private int local_active_cases;
    private int local_total_pcr_test;
    private int local_today_pcr_tests;

    private int global_new_cases;
    private int global_total_cases;
    private int global_deaths;
    private int global_new_deaths;
    private int global_recovered;

    public JsonObjectMapper(JsonObject response) {
        local_new_cases = Integer.parseInt(response.get("data").getAsJsonObject().get("local_new_cases").getAsString());
        local_total_cases = Integer.parseInt(response.get("data").getAsJsonObject().get("local_total_cases").getAsString());
        try{
            local_today_deaths = Integer.parseInt(response.get("data").getAsJsonObject().get("local_today_deaths").getAsString());
        }
        catch(NullPointerException ex)
        {
            local_today_deaths = 0;
        }
        local_total_deaths = Integer.parseInt(response.get("data").getAsJsonObject().get("local_deaths").getAsString());
        local_today_pcr_tests = Integer.parseInt(response.get("data").getAsJsonObject().get("daily_pcr_testing_data").getAsJsonArray().get(response.get("data").getAsJsonObject().get("daily_pcr_testing_data").getAsJsonArray().size() -1).getAsJsonObject().get("count").getAsString());

        global_new_cases = Integer.parseInt(response.get("data").getAsJsonObject().get("global_new_cases").getAsString());
        global_total_cases = Integer.parseInt(response.get("data").getAsJsonObject().get("global_total_cases").getAsString());
        global_deaths = Integer.parseInt(response.get("data").getAsJsonObject().get("global_deaths").getAsString());
        global_new_deaths = Integer.parseInt(response.get("data").getAsJsonObject().get("global_new_deaths").getAsString());

    }

    public int getLocal_new_cases() {
        return local_new_cases;
    }

    public int getLocal_total_cases() {
        return local_total_cases;
    }

    public int getLocal_total_number_of_individuals_in_hospitals() {
        return local_total_number_of_individuals_in_hospitals;
    }

    public int getLocal_total_deaths() {
        return local_total_deaths;
    }

    public int getLocal_today_deaths() {
        return local_today_deaths;
    }

    public int getLocal_recovered() {
        return local_recovered;
    }

    public int getLocal_active_cases() {
        return local_active_cases;
    }

    public int getLocal_total_pcr_test() {
        return local_total_pcr_test;
    }

    public int getLocal_today_pcr_tests() {
        return local_today_pcr_tests;
    }

    public int getGlobal_new_cases() {
        return global_new_cases;
    }

    public int getGlobal_total_cases() {
        return global_total_cases;
    }

    public int getGlobal_deaths() {
        return global_deaths;
    }

    public int getGlobal_new_deaths() {
        return global_new_deaths;
    }

    public int getGlobal_recovered() {
        return global_recovered;
    }
}
