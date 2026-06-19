package io.composelive.reuse.treehouse

import app.cash.zipline.Zipline


public interface HostZiplineBridge {
    public fun bindTo(zipline: Zipline)
}
