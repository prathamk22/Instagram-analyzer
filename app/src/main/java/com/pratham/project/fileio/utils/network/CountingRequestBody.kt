package com.pratham.project.fileio.utils.network

import okhttp3.RequestBody
import okio.BufferedSink
import okio.IOException
import okio.buffer

class CountingRequestBody(
    private val requestBody: RequestBody,
    private val onProgressUpdate: CountingRequestListener
) : RequestBody() {
    override fun contentType() = requestBody.contentType()

    @Throws(IOException::class)
    override fun writeTo(sink: BufferedSink) {
        val countingSink = CountingSink(sink, this, onProgressUpdate)
        val bufferedSink = countingSink.buffer()

        requestBody.writeTo(bufferedSink)

        bufferedSink.flush()
    }

    @Throws(IOException::class)
    override fun contentLength() = requestBody.contentLength()

}
