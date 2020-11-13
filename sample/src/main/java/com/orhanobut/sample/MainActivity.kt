package com.orhanobut.sample

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.orhanobut.logger.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : Activity() {

    var formatStrategy: FormatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false) // (Optional) Whether to show thread info or not. Default true
            .methodCount(0) // (Optional) How many method line to show. Default 2
            .methodOffset(3) // (Optional) Skips some method invokes in stack trace. Default 5
            //        .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
            .tag("My custom tag") // (Optional) Custom tag for each log. Default PRETTY_LOGGER
            .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        btnNormalLog.setOnClickListener {
            Log.d("Tag", "I'm a log which you don't see easily, hehe")
            Log.d("json content", "{ \"key\": 3, \n \"value\": something}")
            Log.d("error", "There is a crash somewhere or any warning")
        }

        btnDemo1.setOnClickListener {
            Logger.clearLogAdapters()
            Logger.addLogAdapter(AndroidLogAdapter())
            Logger.d("message")
        }

        btnDemo2.setOnClickListener {
            Logger.clearLogAdapters()

            Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
                override fun isLoggable(priority: Int, tag: String?): Boolean {
                    return BuildConfig.DEBUG
                }
            })
            Logger.d("no thread info and only 1 method")
        }

        btnDemo3.setOnClickListener {
            Logger.clearLogAdapters()
            formatStrategy = PrettyFormatStrategy.newBuilder()
                    .showThreadInfo(false)
                    .methodCount(0)
                    .build()
            Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
            Logger.i("no thread info and method info")
            Logger.t("tag").e("Custom tag for only one use")
            Logger.json("{ \"key\": 3, \"value\": something}")
            Logger.d(listOf("foo", "bar"))
            val map: MutableMap<String, String> = HashMap()
            map["key"] = "value"
            map["key1"] = "value2"
            Logger.d(map)
        }

        btnDemo4.setOnClickListener {
            Logger.clearLogAdapters()
            formatStrategy = PrettyFormatStrategy.newBuilder()
                    .showThreadInfo(false)
                    .methodCount(0)
                    .tag("MyTag")
                    .build()
            Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
            Logger.w("my log message with my tag")
        }
    }


}